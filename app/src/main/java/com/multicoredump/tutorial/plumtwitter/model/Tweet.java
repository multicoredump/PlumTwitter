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

     String text;
     long id;
     String createdAt;
     User user;
     Integer retweetCount;
     Boolean favorited;
     Integer favoriteCount;
     boolean retweeted;

     Media media;
     Media extendedMedia;

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

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public Media getMedia() {
        return media;
    }

    public Media getExtendedMedia() {
        return extendedMedia;
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
            tweet.favoriteCount = jsonObject.getInt("favorite_count");
            tweet.retweeted = jsonObject.getBoolean("retweeted");

            JSONObject mediaObj = jsonObject.getJSONObject("entities");
            if(mediaObj.has("media")) {
                JSONArray mediaArray = mediaObj.getJSONArray("media");
                tweet.media = Media.fromJson(mediaArray.getJSONObject(0));
            }

            if(jsonObject.has("extended_entities")) {
                JSONObject extMediaObj = jsonObject.getJSONObject("extended_entities");
                if(extMediaObj.has("media")) {
                    JSONArray extendedMediaArray = extMediaObj.getJSONArray("media");
                    tweet.extendedMedia = Media.fromJson(extendedMediaArray.getJSONObject(0));
                }
            }

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

