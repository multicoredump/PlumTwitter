package com.multicoredump.tutorial.plumtwitter.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by radhikak on 3/23/17.
 */

@Parcel
public class Tweet {

    private String text;
    private long id;
    private String createdAt;
    private User user;
    private Integer retweetCount;
    private Boolean favorited;

    public String getText() {
        return text;
    }

    public long getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public static Tweet fromJson(JSONObject jsonObject){
        Tweet tweet = new Tweet();

        try {
            tweet.text = jsonObject.getString("text");
            tweet.id = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
            tweet.retweetCount = jsonObject.getInt("retweet_count");
            tweet.favorited = jsonObject.getBoolean("favorited");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray response) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        for (int i = 0; i< response.length(); i++) {
            try {
                JSONObject object = response.getJSONObject(i);
                Tweet tweet = Tweet.fromJson(object);
                tweets.add(tweet);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }

    @Override
    public String toString() {
        return text;
    }
}

