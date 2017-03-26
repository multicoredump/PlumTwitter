package com.multicoredump.tutorial.plumtwitter.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.adapter.TweetAdapter;
import com.multicoredump.tutorial.plumtwitter.application.PlumTwitterApplication;
import com.multicoredump.tutorial.plumtwitter.databinding.ActivityTimelineBinding;
import com.multicoredump.tutorial.plumtwitter.fragments.ComposeFragment;
import com.multicoredump.tutorial.plumtwitter.model.Tweet;
import com.multicoredump.tutorial.plumtwitter.model.User;
import com.multicoredump.tutorial.plumtwitter.twitter.OnReplyActionListener;
import com.multicoredump.tutorial.plumtwitter.twitter.TwitterRestClient;
import com.multicoredump.tutorial.plumtwitter.utils.EndlessRecyclerViewScrollListener;
import com.multicoredump.tutorial.plumtwitter.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by radhikak on 3/23/17.
 */

public class TimelineActivity extends AppCompatActivity implements ComposeFragment.OnPostTweetListener, OnReplyActionListener {

    private static final String TAG = TimelineActivity.class.getName();

    private TwitterRestClient twitterClient;

    ArrayList<Tweet> tweets;
    private TweetAdapter tweetAdapter;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    LinearLayoutManager mLayoutManager;

    private ActivityTimelineBinding binding;

    @BindView(R.id.rvTweets) RecyclerView rvTweets;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;

    private User currentUser = null;

    Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timeline);
        ButterKnife.bind(this);
        twitterClient = PlumTwitterApplication.getTwitterClient();
        //Setting toolbar
        setSupportActionBar(binding.toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        tweets = new ArrayList<>();
        tweetAdapter = new TweetAdapter(tweets, this);
        rvTweets.setAdapter(tweetAdapter);
        rvTweets.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(mLayoutManager);

        //Recylerview decorater
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvTweets.addItemDecoration(itemDecoration);

        //Endless Scroll listener
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //Handle fetching in a thread with delay to avoid error "API Limit reached" = 429
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateTimeline(getMaxId());
                    }
                }, 1000);
            }
        };
        rvTweets.addOnScrollListener(endlessRecyclerViewScrollListener);

        //Swipe to refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!NetworkUtils.isOnline()) {
                    handleRequestError();
                }

                updateTimeline(0);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComposeFragment composeFragment = ComposeFragment.newInstance(currentUser, null);
                composeFragment.show(TimelineActivity.this.getSupportFragmentManager(), "compose");
            }
        });

        // Get current user info
        getCurrentUser();

        //Fetch first page
        updateTimeline(0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (NetworkUtils.isNetworkAvailable(this) || NetworkUtils.isOnline()) {
            if (snackbar != null && snackbar.isShown()) {
                snackbar.dismiss();
                updateTimeline(0);
            }

        } else {
            handleRequestError();
        }
    }

    private long getMaxId() {
        return tweets.get(tweets.size() - 1).getId();
    }

    private void updateTimeline(final long maxId) {

        twitterClient.getHomeTimeline(maxId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                swipeRefreshLayout.setRefreshing(false);
                if(maxId == 0) {
                    tweets.clear();
                }
                tweets.addAll(Tweet.fromJSONArray(response));
                tweetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                super.onFailure(statusCode, headers, throwable, object);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    JsonHttpResponseHandler postTweetHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Tweet postedTweet = Tweet.fromJson(response);
            if (postedTweet != null) {
                tweets.add(0, postedTweet);
                tweetAdapter.notifyItemInserted(0);
                rvTweets.scrollToPosition(0);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
            handleRequestError();
        }
    };

    @Override
    public JsonHttpResponseHandler getJsonHttpResponseHandler() {
        return postTweetHandler;
    }

    @Override
    public void onReply(final Tweet tweet) {
        ComposeFragment composeFragment = ComposeFragment.newInstance(currentUser, tweet.getUser());
        composeFragment.show(TimelineActivity.this.getSupportFragmentManager(), "reply");
    }

    private void getCurrentUser() {
        twitterClient.getCurrentUser(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentUser = User.fromJson(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                handleRequestError();
            }
        });
    }

    private void handleRequestError() {
        // The request was not successful hence first check if network is connected
        if (!NetworkUtils.isNetworkAvailable(this) || !NetworkUtils.isOnline()) {
            snackbar = Snackbar.make(swipeRefreshLayout, "Network Error. Please connect to Internet and try again", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Wi-Fi Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    });
            snackbar.show();
        }
    }
}
