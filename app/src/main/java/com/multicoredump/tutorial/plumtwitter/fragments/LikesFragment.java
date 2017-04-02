package com.multicoredump.tutorial.plumtwitter.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.multicoredump.tutorial.plumtwitter.application.PlumTwitterApplication;
import com.multicoredump.tutorial.plumtwitter.model.Tweet;
import com.multicoredump.tutorial.plumtwitter.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LikesFragment extends BaseTimelineTabFragment {

    private static final String ARG_USER = "user";
    private User user = null;

    public static LikesFragment newInstance(User user) {
        LikesFragment fragment = new LikesFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, Parcels.wrap(user));
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // save current user
        if (getArguments() != null) {
            user = Parcels.unwrap(getArguments().getParcelable(ARG_USER));
        }
    }

    public LikesFragment() {
        super();
    }

    @Override
    public int getTabPosition() {
        return PlumTwitterApplication.FAV_TAB_POSITION;
    }

    @Override
    public Drawable getTabDrawable() {
        return null;
    }

    @Override
    public String getTabTitle() {
        return "LIKES";
    }

    @Override
    protected void updateTimeline(final long id) {

        twitterClient.getFavoriteTweets(id, user.getScreenName(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                swipeRefreshLayout.setRefreshing(false);
                if(id == 0) {
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
}
