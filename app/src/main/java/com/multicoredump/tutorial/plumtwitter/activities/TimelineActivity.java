package com.multicoredump.tutorial.plumtwitter.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.adapter.TweetFragmentPagerAdapater;
import com.multicoredump.tutorial.plumtwitter.application.PlumTwitterApplication;
import com.multicoredump.tutorial.plumtwitter.databinding.ActivityTimelineBinding;
import com.multicoredump.tutorial.plumtwitter.fragments.BaseTimelineTabFragment;
import com.multicoredump.tutorial.plumtwitter.fragments.ComposeFragment;
import com.multicoredump.tutorial.plumtwitter.fragments.MentionsFragment;
import com.multicoredump.tutorial.plumtwitter.fragments.TimelineFragment;
import com.multicoredump.tutorial.plumtwitter.model.Tweet;
import com.multicoredump.tutorial.plumtwitter.model.User;
import com.multicoredump.tutorial.plumtwitter.twitter.OnReplyActionListener;
import com.multicoredump.tutorial.plumtwitter.utils.NetworkUtils;

import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by radhikak on 3/23/17.
 */

public class TimelineActivity extends AppCompatActivity implements ComposeFragment.OnPostTweetListener,
        OnReplyActionListener,
        BaseTimelineTabFragment.TwitterCurrentUserProvider {

    private static final String TAG = TimelineActivity.class.getName();

    private static String PAGER_INDEX = "Pager_Index";

    private ActivityTimelineBinding binding;

    private User currentUser = null;

    Snackbar snackbar;

    TimelineFragment timelineFragment;
    MentionsFragment mentionsFragment;


    ArrayList<BaseTimelineTabFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timeline);
        ButterKnife.bind(this);

        //Setting toolbar
        setSupportActionBar(binding.toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        timelineFragment = new TimelineFragment();
        mentionsFragment = new MentionsFragment();
        fragments.add(timelineFragment.getTabPosition(), timelineFragment);
        fragments.add(mentionsFragment.getTabPosition(), mentionsFragment);

        // Get the ViewPager and set it's TweetFragmentAdapter so that it can display items
        binding.viewpager.setAdapter(new TweetFragmentPagerAdapater(getSupportFragmentManager(), fragments));

        // Give the TabLayout the ViewPager
        binding.slidingTabs.setupWithViewPager(binding.viewpager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComposeFragment composeFragment = ComposeFragment.newInstance(currentUser, null);
                composeFragment.show(getSupportFragmentManager(), "compose");
            }
        });

        // Get current user info
        loadCurrentUser();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_profile) {
            // start a new activity to show profile
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("user", Parcels.wrap(currentUser));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    JsonHttpResponseHandler postTweetHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Gson gson = new Gson();
            Tweet postedTweet = gson.fromJson(response.toString(), Tweet.class);
            if (postedTweet != null) {
                timelineFragment.insertNewTweet(postedTweet);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
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

    private void loadCurrentUser() {
        PlumTwitterApplication.getTwitterClient().getCurrentUser(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                currentUser = gson.fromJson(response.toString(), User.class);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
            }
        });
    }

    private void handleRequestError() {
        // The request was not successful hence first check if network is connected
        if (!NetworkUtils.isNetworkAvailable(this) || !NetworkUtils.isOnline()) {
            snackbar = Snackbar.make(binding.viewpager, "Network Error. Please connect to Internet and try again", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Wi-Fi Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    });
            snackbar.show();
        }
    }

    // To save tab position
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PAGER_INDEX, binding.slidingTabs.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        binding.viewpager.setCurrentItem(savedInstanceState.getInt(PAGER_INDEX));
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
