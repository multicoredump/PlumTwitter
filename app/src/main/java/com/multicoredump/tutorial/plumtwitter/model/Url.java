package com.multicoredump.tutorial.plumtwitter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Url {

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("expanded_url")
    @Expose
    private String expandedUrl;

    @SerializedName("display_url")
    @Expose
    private String displayUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }


}
