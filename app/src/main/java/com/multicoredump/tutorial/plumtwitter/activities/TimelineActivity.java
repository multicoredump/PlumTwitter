package com.multicoredump.tutorial.plumtwitter.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.adapter.TweetAdapter;
import com.multicoredump.tutorial.plumtwitter.application.PlumTwitterApplication;
import com.multicoredump.tutorial.plumtwitter.databinding.ActivityTimelineBinding;
import com.multicoredump.tutorial.plumtwitter.fragments.ComposeFragment;
import com.multicoredump.tutorial.plumtwitter.model.Tweet;
import com.multicoredump.tutorial.plumtwitter.twitter.TwitterRestClient;
import com.multicoredump.tutorial.plumtwitter.utils.EndlessRecyclerViewScrollListener;
import com.multicoredump.tutorial.plumtwitter.utils.NetworkUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by radhikak on 3/23/17.
 */

public class TimelineActivity extends AppCompatActivity implements ComposeFragment.OnPostTweetListener {

    private static final String TAG = TimelineActivity.class.getName();
    
    private TwitterRestClient client;

    ArrayList<Tweet> tweets;
    private TweetAdapter tweetAdapter;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    LinearLayoutManager mLayoutManager;

    private ActivityTimelineBinding binding;

    @BindView(R.id.rvTweets) RecyclerView rvTweets;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timeline);
        ButterKnife.bind(this);
        client = PlumTwitterApplication.getTwitterClient();
        //Setting toolbar
        setSupportActionBar(binding.toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        tweets = new ArrayList<>();
        tweetAdapter = new TweetAdapter(tweets);
        rvTweets.setAdapter(tweetAdapter);
        rvTweets.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(mLayoutManager);

//        //Recylerview decorater
//        RecyclerView.ItemDecoration itemDecoration =
//                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        rvTweets.addItemDecoration(itemDecoration);

        //Endless Scroll listener
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //Handle fetching in a thread with delay to avoid error "API Limit reached" = 429
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        populateTimeline(false, getMaxId());
                    }
                }, 1000);
            }
        };
        rvTweets.addOnScrollListener(endlessRecyclerViewScrollListener);

        //Swipe to refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!NetworkUtility.isOnline()) {
                    Snackbar.make(binding.cLayout, "Check your internet Connection", Snackbar.LENGTH_LONG).show();
                    return;
                }

                populateTimeline(true, 0);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComposeFragment composeFragment = ComposeFragment.newInstance(null);
                composeFragment.show(TimelineActivity.this.getSupportFragmentManager(),"");
            }
        });

        //Fetch first page
        populateTimeline(true, 0);
    }

    private long getMaxId() {
        return tweets.get(tweets.size() - 1).getUid();
    }

    private void populateTimeline(final Boolean subsequent, long id) {

        client.getHomeTimeline(subsequent, id, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d(TAG, response.toString());

                swipeRefreshLayout.setRefreshing(false);
                if(subsequent) {
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

    @Override
    public void onSuccess(Tweet tweet) {
        // insert this at front
        Log.d(TAG, tweet.toString());
    }
}
