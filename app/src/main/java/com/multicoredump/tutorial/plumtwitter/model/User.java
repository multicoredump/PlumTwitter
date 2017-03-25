
package com.multicoredump.tutorial.plumtwitter.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "profile_sidebar_fill_color",
    "profile_background_tile",
    "profile_sidebar_border_color",
    "profile_image_url",
    "created_at",
    "location",
    "follow_request_sent",
    "id_str",
    "is_translator",
    "profile_link_color",
    "entities",
    "default_profile",
    "url",
    "contributors_enabled",
    "favourites_count",
    "utc_offset",
    "profile_image_url_https",
    "id",
    "listed_count",
    "profile_use_background_image",
    "profile_text_color",
    "followers_count",
    "lang",
    "protected",
    "geo_enabled",
    "notifications",
    "description",
    "profile_background_color",
    "verified",
    "time_zone",
    "profile_background_image_url_https",
    "statuses_count",
    "profile_background_image_url",
    "default_profile_image",
    "friends_count",
    "following",
    "show_all_inline_media",
    "screen_name"
})
public class User {

    @JsonProperty("name")
    private String name;
    @JsonProperty("profile_sidebar_fill_color")
    private String profileSidebarFillColor;
    @JsonProperty("profile_background_tile")
    private Boolean profileBackgroundTile;
    @JsonProperty("profile_sidebar_border_color")
    private String profileSidebarBorderColor;
    @JsonProperty("profile_image_url")
    private String profileImageUrl;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("location")
    private String location;
    @JsonProperty("follow_request_sent")
    private Boolean followRequestSent;
    @JsonProperty("id_str")
    private String idStr;
    @JsonProperty("is_translator")
    private Boolean isTranslator;
    @JsonProperty("profile_link_color")
    private String profileLinkColor;
    @JsonProperty("entities")
    private Entities_ entities;
    @JsonProperty("default_profile")
    private Boolean defaultProfile;
    @JsonProperty("url")
    private String url;
    @JsonProperty("contributors_enabled")
    private Boolean contributorsEnabled;
    @JsonProperty("favourites_count")
    private Integer favouritesCount;
    @JsonProperty("utc_offset")
    private Integer utcOffset;
    @JsonProperty("profile_image_url_https")
    private String profileImageUrlHttps;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("listed_count")
    private Integer listedCount;
    @JsonProperty("profile_use_background_image")
    private Boolean profileUseBackgroundImage;
    @JsonProperty("profile_text_color")
    private String profileTextColor;
    @JsonProperty("followers_count")
    private Integer followersCount;
    @JsonProperty("lang")
    private String lang;
    @JsonProperty("protected")
    private Boolean _protected;
    @JsonProperty("geo_enabled")
    private Boolean geoEnabled;
    @JsonProperty("notifications")
    private Boolean notifications;
    @JsonProperty("description")
    private String description;
    @JsonProperty("profile_background_color")
    private String profileBackgroundColor;
    @JsonProperty("verified")
    private Boolean verified;
    @JsonProperty("time_zone")
    private String timeZone;
    @JsonProperty("profile_background_image_url_https")
    private String profileBackgroundImageUrlHttps;
    @JsonProperty("statuses_count")
    private Integer statusesCount;
    @JsonProperty("profile_background_image_url")
    private String profileBackgroundImageUrl;
    @JsonProperty("default_profile_image")
    private Boolean defaultProfileImage;
    @JsonProperty("friends_count")
    private Integer friendsCount;
    @JsonProperty("following")
    private Boolean following;
    @JsonProperty("show_all_inline_media")
    private Boolean showAllInlineMedia;
    @JsonProperty("screen_name")
    private String screenName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("profile_sidebar_fill_color")
    public String getProfileSidebarFillColor() {
        return profileSidebarFillColor;
    }

    @JsonProperty("profile_sidebar_fill_color")
    public void setProfileSidebarFillColor(String profileSidebarFillColor) {
        this.profileSidebarFillColor = profileSidebarFillColor;
    }

    @JsonProperty("profile_background_tile")
    public Boolean getProfileBackgroundTile() {
        return profileBackgroundTile;
    }

    @JsonProperty("profile_background_tile")
    public void setProfileBackgroundTile(Boolean profileBackgroundTile) {
        this.profileBackgroundTile = profileBackgroundTile;
    }

    @JsonProperty("profile_sidebar_border_color")
    public String getProfileSidebarBorderColor() {
        return profileSidebarBorderColor;
    }

    @JsonProperty("profile_sidebar_border_color")
    public void setProfileSidebarBorderColor(String profileSidebarBorderColor) {
        this.profileSidebarBorderColor = profileSidebarBorderColor;
    }

    @JsonProperty("profile_image_url")
    public String getProfileImageUrl() {
        return profileImageUrl.replace("normal", "bigger");
    }

    @JsonProperty("profile_image_url")
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("follow_request_sent")
    public Boolean getFollowRequestSent() {
        return followRequestSent;
    }

    @JsonProperty("follow_request_sent")
    public void setFollowRequestSent(Boolean followRequestSent) {
        this.followRequestSent = followRequestSent;
    }

    @JsonProperty("id_str")
    public String getIdStr() {
        return idStr;
    }

    @JsonProperty("id_str")
    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    @JsonProperty("is_translator")
    public Boolean getIsTranslator() {
        return isTranslator;
    }

    @JsonProperty("is_translator")
    public void setIsTranslator(Boolean isTranslator) {
        this.isTranslator = isTranslator;
    }

    @JsonProperty("profile_link_color")
    public String getProfileLinkColor() {
        return profileLinkColor;
    }

    @JsonProperty("profile_link_color")
    public void setProfileLinkColor(String profileLinkColor) {
        this.profileLinkColor = profileLinkColor;
    }

    @JsonProperty("entities")
    public Entities_ getEntities() {
        return entities;
    }

    @JsonProperty("entities")
    public void setEntities(Entities_ entities) {
        this.entities = entities;
    }

    @JsonProperty("default_profile")
    public Boolean getDefaultProfile() {
        return defaultProfile;
    }

    @JsonProperty("default_profile")
    public void setDefaultProfile(Boolean defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("contributors_enabled")
    public Boolean getContributorsEnabled() {
        return contributorsEnabled;
    }

    @JsonProperty("contributors_enabled")
    public void setContributorsEnabled(Boolean contributorsEnabled) {
        this.contributorsEnabled = contributorsEnabled;
    }

    @JsonProperty("favourites_count")
    public Integer getFavouritesCount() {
        return favouritesCount;
    }

    @JsonProperty("favourites_count")
    public void setFavouritesCount(Integer favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    @JsonProperty("utc_offset")
    public Integer getUtcOffset() {
        return utcOffset;
    }

    @JsonProperty("utc_offset")
    public void setUtcOffset(Integer utcOffset) {
        this.utcOffset = utcOffset;
    }

    @JsonProperty("profile_image_url_https")
    public String getProfileImageUrlHttps() {
        return profileImageUrlHttps;
    }

    @JsonProperty("profile_image_url_https")
    public void setProfileImageUrlHttps(String profileImageUrlHttps) {
        this.profileImageUrlHttps = profileImageUrlHttps;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("listed_count")
    public Integer getListedCount() {
        return listedCount;
    }

    @JsonProperty("listed_count")
    public void setListedCount(Integer listedCount) {
        this.listedCount = listedCount;
    }

    @JsonProperty("profile_use_background_image")
    public Boolean getProfileUseBackgroundImage() {
        return profileUseBackgroundImage;
    }

    @JsonProperty("profile_use_background_image")
    public void setProfileUseBackgroundImage(Boolean profileUseBackgroundImage) {
        this.profileUseBackgroundImage = profileUseBackgroundImage;
    }

    @JsonProperty("profile_text_color")
    public String getProfileTextColor() {
        return profileTextColor;
    }

    @JsonProperty("profile_text_color")
    public void setProfileTextColor(String profileTextColor) {
        this.profileTextColor = profileTextColor;
    }

    @JsonProperty("followers_count")
    public Integer getFollowersCount() {
        return followersCount;
    }

    @JsonProperty("followers_count")
    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    @JsonProperty("lang")
    public String getLang() {
        return lang;
    }

    @JsonProperty("lang")
    public void setLang(String lang) {
        this.lang = lang;
    }

    @JsonProperty("protected")
    public Boolean getProtected() {
        return _protected;
    }

    @JsonProperty("protected")
    public void setProtected(Boolean _protected) {
        this._protected = _protected;
    }

    @JsonProperty("geo_enabled")
    public Boolean getGeoEnabled() {
        return geoEnabled;
    }

    @JsonProperty("geo_enabled")
    public void setGeoEnabled(Boolean geoEnabled) {
        this.geoEnabled = geoEnabled;
    }

    @JsonProperty("notifications")
    public Boolean getNotifications() {
        return notifications;
    }

    @JsonProperty("notifications")
    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("profile_background_color")
    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }

    @JsonProperty("profile_background_color")
    public void setProfileBackgroundColor(String profileBackgroundColor) {
        this.profileBackgroundColor = profileBackgroundColor;
    }

    @JsonProperty("verified")
    public Boolean getVerified() {
        return verified;
    }

    @JsonProperty("verified")
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @JsonProperty("time_zone")
    public String getTimeZone() {
        return timeZone;
    }

    @JsonProperty("time_zone")
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @JsonProperty("profile_background_image_url_https")
    public String getProfileBackgroundImageUrlHttps() {
        return profileBackgroundImageUrlHttps;
    }

    @JsonProperty("profile_background_image_url_https")
    public void setProfileBackgroundImageUrlHttps(String profileBackgroundImageUrlHttps) {
        this.profileBackgroundImageUrlHttps = profileBackgroundImageUrlHttps;
    }

    @JsonProperty("statuses_count")
    public Integer getStatusesCount() {
        return statusesCount;
    }

    @JsonProperty("statuses_count")
    public void setStatusesCount(Integer statusesCount) {
        this.statusesCount = statusesCount;
    }

    @JsonProperty("profile_background_image_url")
    public String getProfileBackgroundImageUrl() {
        return profileBackgroundImageUrl;
    }

    @JsonProperty("profile_background_image_url")
    public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
        this.profileBackgroundImageUrl = profileBackgroundImageUrl;
    }

    @JsonProperty("default_profile_image")
    public Boolean getDefaultProfileImage() {
        return defaultProfileImage;
    }

    @JsonProperty("default_profile_image")
    public void setDefaultProfileImage(Boolean defaultProfileImage) {
        this.defaultProfileImage = defaultProfileImage;
    }

    @JsonProperty("friends_count")
    public Integer getFriendsCount() {
        return friendsCount;
    }

    @JsonProperty("friends_count")
    public void setFriendsCount(Integer friendsCount) {
        this.friendsCount = friendsCount;
    }

    @JsonProperty("following")
    public Boolean getFollowing() {
        return following;
    }

    @JsonProperty("following")
    public void setFollowing(Boolean following) {
        this.following = following;
    }

    @JsonProperty("show_all_inline_media")
    public Boolean getShowAllInlineMedia() {
        return showAllInlineMedia;
    }

    @JsonProperty("show_all_inline_media")
    public void setShowAllInlineMedia(Boolean showAllInlineMedia) {
        this.showAllInlineMedia = showAllInlineMedia;
    }

    @JsonProperty("screen_name")
    public String getScreenName() {
        return screenName;
    }

    @JsonProperty("screen_name")
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public static User fromJson(JSONObject jsonObject) {
        User user = new User();

        try {
            user.name = jsonObject.getString("name");
            user.id = jsonObject.getInt("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
            user.verified = jsonObject.getBoolean("verified");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }


}
