package com.multicoredump.tutorial.plumtwitter.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;


@Parcel
public class Media {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("type")
    @Expose
    String type;

    @SerializedName("media_url_https")
    @Expose
    String mediaUrlHttps;

//    @SerializedName("video_info")
//    @Expose
//    VideoInfo videoInfo;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getMediaUrlHttps() {
        return mediaUrlHttps;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMediaUrlHttps(String mediaUrlHttps) {
        this.mediaUrlHttps = mediaUrlHttps;
    }


}

