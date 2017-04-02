package com.multicoredump.tutorial.plumtwitter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.multicoredump.tutorial.plumtwitter.db.PlumTwitterDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import org.parceler.Parcel;

/**
 * Created by radhikak on 3/23/17.
 */

@Table(database = PlumTwitterDatabase.class)
@Parcel
public class User {


    @Column
    @SerializedName("name")
    @Expose
    private String name;

    @Column
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private long id;

    @Column
    @SerializedName("screen_name")
    @Expose
    private String screenName;

    @Column
    @SerializedName("profile_image_url")
    @Expose
    private String profileImageURL;

    @Column
    @SerializedName("verified")
    @Expose
    private boolean verified;

    @Column
    @SerializedName("profile_banner_url")
    @Expose
    private String coverImageURL;

    @Column
    @SerializedName("followers_count")
    @Expose
    private String followerCount;

    @Column
    @SerializedName("friends_count")
    @Expose
    private String followingCount;

    @Column
    @SerializedName("description")
    @Expose
    private String description;

    @Column
    @SerializedName("location")
    @Expose
    private String location;

    @Column
    @SerializedName("following")
    @Expose
    private boolean following;

    @Column
    @SerializedName("follow_request_sent")
    @Expose
    private boolean follow_request_sent;

    public User() {
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileNormalImageURL() {
        return profileImageURL;
    }

    public String getProfileBiggerImageURL() {
        return profileImageURL.replace("_normal", "_bigger");
    }

    public Boolean isVerified() {
        return verified;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getCoverImageURL() {
        return coverImageURL;
    }

    public void setCoverImageURL(String coverImageURL) {
        this.coverImageURL = coverImageURL;
    }

    public String getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(String followerCount) {
        this.followerCount = followerCount;
    }

    public String getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(String followingCount) {
        this.followingCount = followingCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public boolean isFollow_request_sent() {
        return follow_request_sent;
    }

    public void setFollow_request_sent(boolean follow_request_sent) {
        this.follow_request_sent = follow_request_sent;
    }
}
