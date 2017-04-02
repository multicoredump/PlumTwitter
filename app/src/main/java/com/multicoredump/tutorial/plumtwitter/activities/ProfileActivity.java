package com.multicoredump.tutorial.plumtwitter.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.databinding.ActivityProfileBinding;
import com.multicoredump.tutorial.plumtwitter.fragments.UserTimelineFragment;
import com.multicoredump.tutorial.plumtwitter.model.User;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        //Get user info
        Intent intent = getIntent();
        user = Parcels.unwrap(intent.getParcelableExtra("user"));

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Backdrop
        Glide.with(this)
                .load(user.getCoverImageURL())
                .into(binding.backdrop);

        //Profile image
        Glide.with(this)
                .load(user.getProfileBiggerImageURL())
                .into(binding.profileImage);

        binding.followerCount.setText(user.getFollowerCount());
        binding.followingCount.setText(user.getFollowingCount());
        binding.userName.setText(user.getName());
        binding.screenName.setText("@" + user.getScreenName());
        if (null != user.getDescription()) {
            binding.description.setVisibility(View.VISIBLE);
            binding.description.setText(user.getDescription());
        }else{
            binding.description.setVisibility(View.GONE);
        }

        if (null != user.getLocation()) {
            binding.locationIcon.setVisibility(View.VISIBLE);
            binding.location.setText(user.getLocation());
        } else {
            binding.locationIcon.setVisibility(View.GONE);
        }
        setupViewPager(binding.viewpager);
        binding.tabs.setupWithViewPager(binding.viewpager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ProfilePagerAdapter adapter = new ProfilePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(UserTimelineFragment.newInstance(user), "TWEETS");
//        adapter.addFragment(new PhotosFragment(), "PHOTOS");
//        adapter.addFragment(new FavoritesFragment(), "FAVORITES");
        viewPager.setAdapter(adapter);
    }

    private class ProfilePagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ProfilePagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
