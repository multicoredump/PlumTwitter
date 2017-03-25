package com.multicoredump.tutorial.plumtwitter.adapter;

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
import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.model.Tweet;
import com.multicoredump.tutorial.plumtwitter.utils.DateFormatting;
import com.multicoredump.tutorial.plumtwitter.utils.PatternEditableBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by radhikak on 3/23/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {

    private static final String TAG = TweetAdapter.class.getName();
    private List<Tweet> tweets;

    public class TweetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivProfile)
        ImageButton ivProfile;
        @BindView(R.id.tvUsername) TextView tvUsername;
        @BindView(R.id.tvBody) TextView tvBody;
        @BindView(R.id.tvTimestamp) TextView tvTimestamp;
        @BindView(R.id.tvScreenName) TextView tvScreenName;
        @BindView(R.id.ivVerified) ImageView ivVerified;

        @BindView(R.id.tvRetweetCount) TextView tvRetweetCount;

        public TweetViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public TweetAdapter(ArrayList<Tweet> tweetList) {
        tweets = tweetList;
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

        Log.d(TAG, "Profile Image URL: " + tweet.getUser().getProfileOriginalImageURL());

        if (tweet != null) {
            Glide.with(holder.itemView.getContext())
                    .load(tweet.getUser().getProfileOriginalImageURL())
                    .bitmapTransform(new RoundedCornersTransformation(holder.itemView.getContext(), 5, 0))
                    .into(holder.ivProfile);

            holder.tvScreenName.setText("@" + tweet.getUser().getScreenName());
            holder.tvUsername.setText(tweet.getUser().getName());
            holder.tvBody.setText(tweet.getBody());
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

        }
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
}
