package com.multicoredump.tutorial.plumtwitter.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.activities.ProfileActivity;
import com.multicoredump.tutorial.plumtwitter.application.PlumTwitterApplication;
import com.multicoredump.tutorial.plumtwitter.model.Tweet;
import com.multicoredump.tutorial.plumtwitter.twitter.OnReplyActionListener;
import com.multicoredump.tutorial.plumtwitter.utils.DateFormatting;
import com.multicoredump.tutorial.plumtwitter.utils.PatternEditableBuilder;

import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by radhikak on 3/23/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {

    private static final String TAG = TweetAdapter.class.getName();

    private List<Tweet> tweets;

    private OnReplyActionListener onReplyActionListener;

    public class TweetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivProfile)
        ImageView ivProfile;
        @BindView(R.id.tvUsername) TextView tvUsername;
        @BindView(R.id.tvBody) TextView tvBody;
        @BindView(R.id.tvTimestamp) TextView tvTimestamp;
        @BindView(R.id.tvScreenName) TextView tvScreenName;
        @BindView(R.id.ivVerified) ImageView ivVerified;

        @BindView(R.id.tvRetweetCount) TextView tvRetweetCount;

        @BindView(R.id.tvFavoriteCount) TextView tvFavoriteCount;

        @BindView(R.id.ibFavorite) ImageButton ibFavorite;
        @BindView(R.id.ibRetweet) ImageButton ibRetweet;
        @BindView(R.id.ibReply) ImageButton ibReply;

        @BindView(R.id.ivTweetImage) ImageView ivTweetImage;


        public TweetViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public TweetAdapter(ArrayList<Tweet> tweetList, OnReplyActionListener listener) {
        tweets = tweetList;

        if (listener == null) throw new IllegalArgumentException("OnReplyActionListener cannot be null!!");
        onReplyActionListener = listener;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tweet, parent, false);

        return new TweetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TweetViewHolder holder, int position) {
        final Tweet tweet = tweets.get(position);

        final Context context = holder.itemView.getContext();
        final Resources resources = context.getResources();

        Log.d(TAG, "Profile Image URL: " + tweet.getUser().getProfileBiggerImageURL());

        if (tweet != null) {
            Glide.with(holder.itemView.getContext())
                    .load(tweet.getUser().getProfileBiggerImageURL())
                    .bitmapTransform(new RoundedCornersTransformation(holder.itemView.getContext(), 5, 0))
                    .into(holder.ivProfile);

            holder.tvScreenName.setText("@" + tweet.getUser().getScreenName());
            holder.tvUsername.setText(tweet.getUser().getName());
            holder.tvBody.setText(tweet.getText());
            // Style clickable spans based on pattern
            new PatternEditableBuilder().
                    addPattern(Pattern.compile("\\@(\\w+)"), Color.BLUE, null)
                    .addPattern(Pattern.compile("\\#(\\w+)"), Color.GRAY, null)
                    .into(holder.tvBody);

            holder.tvTimestamp.setText(DateFormatting.getRelativeTime(tweet.getCreatedAt()));

            if (tweet.getUser().isVerified()) {
                holder.ivVerified.setVisibility(View.VISIBLE);
            } else {
                holder.ivVerified.setVisibility(View.INVISIBLE);
            }

            if (tweet.getRetweetCount() != 0) {
                holder.tvRetweetCount.setText(tweet.getRetweetCount().toString());
            } else {
                holder.tvRetweetCount.setText("");
            }

            holder.ibReply.setColorFilter(resources.getColor(R.color.colorPrimary));
            holder.ibRetweet.setColorFilter(resources.getColor(R.color.colorAccent));
            holder.ibFavorite.setColorFilter(resources.getColor(R.color.colorHighlight));

            if (tweet.isRetweeted()) {
                holder.ibRetweet.setAlpha(1.0f);
            } else {
                holder.ibRetweet.setAlpha(0.5f);
            }

            if (tweet.getFavorited()) {
                holder.ibFavorite.setAlpha(1.0f);
            } else {
                holder.ibFavorite.setAlpha(0.5f);
            }

            if (tweet.getFavoriteCount() != 0) {
                holder.tvFavoriteCount.setText(tweet.getFavoriteCount().toString());
            } else {
                holder.tvFavoriteCount.setText("");
            }

            // Check if multimedia image is available
            if(tweet.getEntities()!=null && tweet.getEntities().getMedia()!=null &&
                    !tweet.getEntities().getMedia().isEmpty()  &&
                    tweet.getEntities().getMedia().get(0).getMediaUrlHttps()!=null) {
                Glide.with(context)
                        .load(tweet.getEntities().getMedia().get(0).getMediaUrlHttps())
                        .bitmapTransform(new RoundedCornersTransformation(context, 20, 0))
                        .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                        .into(holder.ivTweetImage);
                holder.ivTweetImage.setVisibility(View.VISIBLE);
            } else {
                holder.ivTweetImage.setVisibility(View.GONE);
            }

            holder.ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // TODO avoid launching for same user

                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra("user", Parcels.wrap(tweet.getUser()));
                    context.startActivity(intent);
                }
            });


            holder.ibRetweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlumTwitterApplication.getTwitterClient().postRetweet(tweet.getId(), new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            // only change retweet count of original tweet

                            Gson gson = new Gson();
                            Tweet retweet = gson.fromJson(response.toString(), Tweet.class);
                            holder.tvRetweetCount.setText(retweet.getRetweetCount().toString());
                            holder.ibRetweet.setAlpha(1.0f);

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                            Log.e(TAG, "Retweet action failed", throwable);
                        }
                    });
                }
            });
//
            holder.ibFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (tweet.getFavorited()) {
                        PlumTwitterApplication.getTwitterClient().postFavoriteDestroy(tweet.getId(), new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                // only change retweet count of original tweet

                                Gson gson = new Gson();
                                Tweet retweet = gson.fromJson(response.toString(), Tweet.class);
                                holder.tvFavoriteCount.setText(retweet.getFavoriteCount().toString());
                                holder.ibFavorite.setAlpha(0.5f);

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                                Log.e(TAG, "Favorite action failed", throwable);
                            }
                        });
                    } else {

                        PlumTwitterApplication.getTwitterClient().postFavoriteCreate(tweet.getId(), new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                // only change retweet count of original tweet

                                Gson gson = new Gson();
                                Tweet retweet = gson.fromJson(response.toString(), Tweet.class);
                                holder.tvFavoriteCount.setText(retweet.getFavoriteCount().toString());
                                holder.ibFavorite.setAlpha(1.0f);

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                                Log.e(TAG, "Favorite action failed", throwable);
                            }
                        });
                    }

                }
            });


            holder.ibReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onReplyActionListener.onReply(tweet);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
}
