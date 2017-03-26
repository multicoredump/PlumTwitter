package com.multicoredump.tutorial.plumtwitter.model;


import com.multicoredump.tutorial.plumtwitter.db.PlumTwitterDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Table(database = PlumTwitterDatabase.class)
@Parcel
public class Media {

    @PrimaryKey
    @Column
    Long id;

    @Column
    String type;

    @Column
    String mediaUrlHttps;

    @Column
    String videoUrlHttps;

    @Column
    String imageUrl;

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getMediaUrlHttps() {
        return mediaUrlHttps;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrlHttps() {
        return videoUrlHttps;
    }

    public static Media fromJson(JSONObject jsonObject){
        Media media = new Media();

        try {
            media.id = jsonObject.getLong("id");
            media.type = jsonObject.getString("type");
            media.imageUrl = jsonObject.getString("media_url");
            media.mediaUrlHttps = jsonObject.getString("media_url_https");

            //If media type video
            if(media.type.equals("video"))
                media.videoUrlHttps = jsonObject.getJSONObject("video_info").
                                        getJSONArray("variants").getJSONObject(0).getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return media;
    }
}
