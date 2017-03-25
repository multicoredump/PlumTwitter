package com.multicoredump.tutorial.plumtwitter.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by radhikak on 3/23/17.
 */

@Parcel
public class User {

    private String name;
    private long uid;
    private String screenName;
    private String profileImageURL;
    private Boolean verified;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileNormalImageURL() {
        return profileImageURL;
    }

    public String getProfileOriginalImageURL() {
        return profileImageURL.replace("_normal", "");
    }

    public Boolean isVerified() {
        return verified;
    }

    public static User fromJson(JSONObject jsonObject) {
        User user = new User();

        try {
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageURL = jsonObject.getString("profile_image_url");
            user.verified = jsonObject.getBoolean("verified");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
