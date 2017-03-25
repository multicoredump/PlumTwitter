package com.multicoredump.tutorial.plumtwitter.twitter;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/**
 * Created by radhikak on 3/23/17.
 */

/*
 *
 * This is the object responsible for communicating with a REST API.
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes:
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 *
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 *
 */
public class TwitterRestClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = "LqYEmliLgy6DkLKkykR6AdYHC";
    public static final String REST_CONSUMER_SECRET = "nsCzKHwAeN7D1M9UxDBCkQx1cOfSwy4sf5atntWfv62Ajwz9Cq";

    public static final String REST_CALLBACK_URL = "oauth://plumtweets";

    public TwitterRestClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getHomeTimeline(Boolean subsequent, long id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);

        //only for subsequent requests
        if (!subsequent) {
            params.put("max_id", id);
        }

        getClient().get(apiUrl, params, handler);
    }

    public void postTweet(String message, AsyncHttpResponseHandler handler) {

        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", message);
        getClient().post(apiUrl, params, handler);
    }

    // get current user info - uses
    public void getCurrentUser(AsyncHttpResponseHandler handler) {

        String apiUrl = getApiUrl("account/verify_credentials.json");
        RequestParams params = new RequestParams();
        getClient().get(apiUrl, params, handler);
    }

}
