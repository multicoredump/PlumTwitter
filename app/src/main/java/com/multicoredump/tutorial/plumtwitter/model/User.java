package com.multicoredump.tutorial.plumtwitter.model;

import com.multicoredump.tutorial.plumtwitter.db.PlumTwitterDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by radhikak on 3/23/17.
 */

@Table(database = PlumTwitterDatabase.class)
@Parcel
public class User {


    @Column
    @PrimaryKey
    long id;

    @Column
     String name;


    @Column
     String screenName;

    @Column
     String profileImageURL;

    @Column
     Boolean verified;

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

    public static User fromJson(JSONObject jsonObject) {
        User user = new User();

        try {
            user.name = jsonObject.getString("name");
            user.id = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageURL = jsonObject.getString("profile_image_url");
            user.verified = jsonObject.getBoolean("verified");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
