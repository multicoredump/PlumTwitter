package com.multicoredump.tutorial.plumtwitter.model;


import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Media {
    String type;
    String mediaUrlHttps;
    String videoUrlHttps;
    String imageUrl;


    public Media() {
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
