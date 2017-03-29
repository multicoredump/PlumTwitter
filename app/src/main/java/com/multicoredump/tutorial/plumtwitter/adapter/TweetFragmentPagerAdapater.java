package com.multicoredump.tutorial.plumtwitter.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.multicoredump.tutorial.plumtwitter.fragments.TweetTabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by radhikak on 3/29/17.
 */

public class TweetFragmentPagerAdapater extends FragmentPagerAdapter {

    private List<TweetTabFragment> fragments;

    public interface TweetPagerTab {
        int getTabPosition();
        Drawable getTabDrawable();
        String getTabTitle();
    }

    public TweetFragmentPagerAdapater(FragmentManager fm, ArrayList<TweetTabFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        TweetTabFragment fragment = fragments.get(position);

        Drawable image =  fragment.getTabDrawable();
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString("   " + fragment.getTabTitle());
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }
}
