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

    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);

        PlumTwitterApplication.context = this;
    }

    public static TwitterRestClient getTwitterClient() {
        return (TwitterRestClient) TwitterRestClient.getInstance(TwitterRestClient.class, PlumTwitterApplication.context);
    }

    public static Context getContext() {
        return context;
    }
}
