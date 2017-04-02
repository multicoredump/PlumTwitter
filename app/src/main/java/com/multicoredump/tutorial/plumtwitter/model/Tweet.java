package com.multicoredump.tutorial.plumtwitter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.multicoredump.tutorial.plumtwitter.db.PlumTwitterDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import org.parceler.Parcel;

/**
 * Created by radhikak on 3/23/17.
 */

@Table(database = PlumTwitterDatabase.class)
@Parcel
public class Tweet {

    @Column
    @SerializedName("text")
    @Expose
     String text;

    @Column
    @PrimaryKey
    @SerializedName("id")
    @Expose
     long id;

    @Column
    @SerializedName("created_at")
    @Expose
     String createdAt;

    @ForeignKey(saveForeignKeyModel = true)
    @Column
    @SerializedName("user")
    @Expose
     User user;

    @Column
    @SerializedName("retweet_count")
    @Expose
     Integer retweetCount;

    @Column
    @SerializedName("favorited")
    @Expose
     Boolean favorited;

    @Column
    @SerializedName("favorite_count")
    @Expose
     Integer favoriteCount;

    @Column
    @SerializedName("retweeted")
    @Expose
     boolean retweeted;


    @Column
    @SerializedName("")
    @Expose
    private int messageCount;

    @SerializedName("entities")
    @Expose
    private Entities entities;

    @SerializedName("extended_entities")
    @Expose
    private ExtendedEntities extendedEntities;

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

    public int getMessageCount() {
        return messageCount;
    }

    public Entities getEntities() {
        return entities;
    }

    public ExtendedEntities getExtendedEntities() {
        return extendedEntities;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public void setExtendedEntities(ExtendedEntities extendedEntities) {
        this.extendedEntities = extendedEntities;
    }

    public Tweet() {}

    @Override
    public String toString() {
        return text;
    }
}

