package com.multicoredump.tutorial.plumtwitter.model;

import com.multicoredump.tutorial.plumtwitter.db.PlumTwitterDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by radhikak on 3/23/17.
 */

@Table(database = PlumTwitterDatabase.class)
@Parcel
public class Tweet {

    @Column
     String text;

    @Column
    @PrimaryKey
     long id;

    @Column
     String createdAt;

    @Column
    @ForeignKey(saveForeignKeyModel = true)
     User user;

    @Column
     Integer retweetCount;

    @Column
     Boolean favorited;

    @Column
     Integer favoriteCount;

    @Column
     boolean retweeted;

    @Column
    @ForeignKey(saveForeignKeyModel = true)
     Media media;

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

            JSONObject jsonMediaObj = jsonObject.getJSONObject("entities");
            if(jsonMediaObj.has("media")) {
                JSONArray mediaArray = jsonMediaObj.getJSONArray("media");
                tweet.media = Media.fromJson(mediaArray.getJSONObject(0));
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

