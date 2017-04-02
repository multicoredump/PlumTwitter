package com.multicoredump.tutorial.plumtwitter.fragments;


import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.application.PlumTwitterApplication;
import com.multicoredump.tutorial.plumtwitter.model.Tweet;
import com.multicoredump.tutorial.plumtwitter.twitter.OnReplyActionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends BaseTimelineTabFragment implements OnReplyActionListener {

    private static final String TAG = TimelineFragment.class.getName();

    public TimelineFragment() {
        // Required empty public constructor
        super();
    }

    protected void updateTimeline(final long maxId) {

        twitterClient.getHomeTimeline(maxId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                swipeRefreshLayout.setRefreshing(false);
                if(maxId == 0) {
                    tweets.clear();
                }

                ArrayList<Tweet> newTweets = new ArrayList<>();
                Gson gson = new Gson();
                for(int i = 0; i < response.length(); i++) {
                    try {
                        Tweet tweet = gson.fromJson(response.getJSONObject(i).toString(),Tweet.class);
                        newTweets.add(tweet);
                    } catch (JSONException e) {
                    }
                }

                tweets.addAll(newTweets);
                tweetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                super.onFailure(statusCode, headers, throwable, object);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public static TimelineFragment newInstance() {
        return new TimelineFragment();
    }

    public void insertNewTweet(Tweet tweet) {
        tweets.add(0, tweet);
        tweetAdapter.notifyItemInserted(0);
        rvTweets.scrollToPosition(0);
    }

    @Override
    public Drawable getTabDrawable() {
        return ContextCompat.getDrawable(PlumTwitterApplication.getContext(), R.drawable.home);
    }

    @Override
    public String getTabTitle() {
        return "Home";
    }

    @Override
    public int getTabPosition() {
        return 0;
    }

    @Override
    public void onReply(Tweet tweet) {

    }
}
