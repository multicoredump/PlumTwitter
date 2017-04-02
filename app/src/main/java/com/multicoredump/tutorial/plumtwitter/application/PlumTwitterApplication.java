package com.multicoredump.tutorial.plumtwitter.application;

import android.app.Application;
import android.content.Context;

import com.multicoredump.tutorial.plumtwitter.twitter.TwitterRestClient;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by radhikak on 3/23/17.
 */

public class PlumTwitterApplication extends Application {
    private static Context context;

    // inside timeline activity
    public static final int HOME_TAB_POSITION = 0;
    public static final int MENTIONS_TAB_POSITION = 1;


    public static final int USER_TWEETS_TAB_POSITION = 0;
    public static final int FAV_TAB_POSITION = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);

        context = this;
    }

    public static TwitterRestClient getTwitterClient() {
        return (TwitterRestClient) TwitterRestClient.getInstance(TwitterRestClient.class, context);
    }

    public static Context getContext() {
        return context;
    }
}
