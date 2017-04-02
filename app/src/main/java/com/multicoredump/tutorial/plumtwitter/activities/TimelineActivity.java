package com.multicoredump.tutorial.plumtwitter.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.adapter.TweetAdapter;
import com.multicoredump.tutorial.plumtwitter.adapter.TweetFragmentPagerAdapater;
import com.multicoredump.tutorial.plumtwitter.application.PlumTwitterApplication;
import com.multicoredump.tutorial.plumtwitter.databinding.ActivityTimelineBinding;
import com.multicoredump.tutorial.plumtwitter.fragments.ComposeFragment;
import com.multicoredump.tutorial.plumtwitter.fragments.MentionsFragment;
import com.multicoredump.tutorial.plumtwitter.fragments.TimelineFragment;
import com.multicoredump.tutorial.plumtwitter.fragments.TimelineTabFragment;
import com.multicoredump.tutorial.plumtwitter.model.Tweet;
import com.multicoredump.tutorial.plumtwitter.model.User;
import com.multicoredump.tutorial.plumtwitter.twitter.OnReplyActionListener;
import com.multicoredump.tutorial.plumtwitter.twitter.TwitterRestClient;
import com.multicoredump.tutorial.plumtwitter.utils.EndlessRecyclerViewScrollListener;
import com.multicoredump.tutorial.plumtwitter.utils.NetworkUtils;

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

    public static String PAGER_INDEX = "Pager_Index";

    private TwitterRestClient twitterClient;

    ArrayList<Tweet> tweets;
    private TweetAdapter tweetAdapter;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    LinearLayoutManager mLayoutManager;

    private ActivityTimelineBinding binding;

    private User currentUser = null;

    Snackbar snackbar;

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    ArrayList<TimelineTabFragment> fragments = new ArrayList<>();

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

        TimelineTabFragment timelineFragment = TimelineFragment.newInstance();
        TimelineTabFragment mentionsFragment = new MentionsFragment();
        fragments.add(timelineFragment.getTabPosition(), timelineFragment);
        fragments.add(mentionsFragment.getTabPosition(), mentionsFragment);

        // Get the ViewPager and set it's TweetFragmentAdapter so that it can display items
        viewPager.setAdapter(new TweetFragmentPagerAdapater(getSupportFragmentManager(), fragments));

        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);

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
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (NetworkUtils.isNetworkAvailable(this) || NetworkUtils.isOnline()) {
            if (snackbar != null && snackbar.isShown()) {
                snackbar.dismiss();
//                updateTimeline(0);
            }

        } else {
            handleRequestError();
        }
    }

    JsonHttpResponseHandler postTweetHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Tweet postedTweet = Tweet.fromJson(response);
            if (postedTweet != null) {
//                tweets.add(0, postedTweet);
//                tweetAdapter.notifyItemInserted(0);
//                rvTweets.scrollToPosition(0);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
//            handleRequestError();
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
            snackbar = Snackbar.make(viewPager, "Network Error. Please connect to Internet and try again", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Wi-Fi Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    });
            snackbar.show();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PAGER_INDEX, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(PAGER_INDEX));
    }
}
