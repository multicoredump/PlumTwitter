package com.multicoredump.tutorial.plumtwitter.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.adapter.TweetFragmentPagerAdapater;
import com.multicoredump.tutorial.plumtwitter.databinding.ActivityProfileBinding;
import com.multicoredump.tutorial.plumtwitter.fragments.BaseTimelineTabFragment;
import com.multicoredump.tutorial.plumtwitter.fragments.ComposeFragment;
import com.multicoredump.tutorial.plumtwitter.fragments.LikesFragment;
import com.multicoredump.tutorial.plumtwitter.fragments.UserTimelineFragment;
import com.multicoredump.tutorial.plumtwitter.model.User;

import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity implements ComposeFragment.OnPostTweetListener,
        BaseTimelineTabFragment.TwitterCurrentUserProvider{

    private static final String TAG = ProfileActivity.class.getName();
    private static String PAGER_INDEX = "Pager_Index";

    ActivityProfileBinding binding;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        //Get user argument
        Intent intent = getIntent();
        user = Parcels.unwrap(intent.getParcelableExtra("user"));

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Backdrop image
        Glide.with(this)
                .load(user.getCoverImageURL())
                .into(binding.backdrop);

        //Profile image
        Glide.with(this)
                .load(user.getProfileBiggerImageURL())
                .into(binding.ivProfileImage);

        binding.tvUsername.setText(user.getName());
        binding.tvScreenName.setText("@" + user.getScreenName());

        binding.tvFollowerCount.setText(user.getFollowerCount());
        binding.followingCount.setText(user.getFollowingCount());

        // description
        if (user.getDescription() != null && !user.getDescription().isEmpty()) {
            binding.tvDescription.setVisibility(View.VISIBLE);
            binding.tvDescription.setText(user.getDescription());
        } else{
            binding.tvDescription.setVisibility(View.GONE);
        }

        // location
        if (user.getLocation() != null && !user.getLocation().isEmpty()) {
            binding.ivLocation.setVisibility(View.VISIBLE);
            binding.ivLocation.setColorFilter(getResources().getColor(R.color.colorPrimary));
            binding.tvLocation.setText(user.getLocation());
        } else {
            binding.ivLocation.setVisibility(View.GONE);
            binding.tvLocation.setVisibility(View.GONE);
        }

        // Set up fragments and view pager
        List<BaseTimelineTabFragment> fragments = new ArrayList<>();
        BaseTimelineTabFragment userTimelineFragment = UserTimelineFragment.newInstance(user);
        BaseTimelineTabFragment favoritesFragment = LikesFragment.newInstance(user);

        fragments.add(userTimelineFragment.getTabPosition(), userTimelineFragment);
        fragments.add(favoritesFragment.getTabPosition(), favoritesFragment);

        ProfilePagerAdapter adapter = new ProfilePagerAdapter(getSupportFragmentManager(), fragments);
        binding.viewpager.setAdapter(adapter);
        binding.tabs.setupWithViewPager(binding.viewpager);

        // set FAB
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComposeFragment composeFragment = ComposeFragment.newInstance(user, null);
                composeFragment.show(getSupportFragmentManager(), "compose");
            }
        });
    }

    // To save tab position
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PAGER_INDEX, binding.tabs.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        binding.viewpager.setCurrentItem(savedInstanceState.getInt(PAGER_INDEX));
    }

    @Override
    public JsonHttpResponseHandler getJsonHttpResponseHandler() {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
            }
        };
    }

    @Override
    public User getCurrentUser() {
        return user;
    }

    private class ProfilePagerAdapter extends TweetFragmentPagerAdapater {
        ProfilePagerAdapter(FragmentManager fm, List<BaseTimelineTabFragment> fragments) {
            super(fm, fragments);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).getTabTitle();
        }
    }
}
