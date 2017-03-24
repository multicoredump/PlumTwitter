package com.multicoredump.tutorial.plumtwitter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.model.Tweet;

import java.util.ArrayList;

/**
 * Created by radhikak on 3/23/17.
 */

public class TweetAdapter extends
        RecyclerView.Adapter<TweetAdapter.MyViewHolder> {

    private ArrayList<Tweet> tweetList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, tweetBody;
        public ImageView userProfileImage;

        public MyViewHolder(View view) {
            super(view);
            userName = (TextView) view.findViewById(R.id.userName);
            tweetBody = (TextView) view.findViewById(R.id.tweetBody);
            userProfileImage = (ImageView) view.findViewById(R.id.profileImage);
        }
    }

    public TweetAdapter(Context context, ArrayList<Tweet> tweetList) {
        this.tweetList = tweetList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tweet, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Tweet tweet = tweetList.get(position);

        if (tweet != null) {
            Glide.with(context)
                    .load(tweet.getUser().getProfileImageURL())
                    .into(holder.userProfileImage);

            holder.userName.setText(tweet.getUser().getScreenName());

            holder.tweetBody.setText(tweet.getBody());
        }
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }
}
