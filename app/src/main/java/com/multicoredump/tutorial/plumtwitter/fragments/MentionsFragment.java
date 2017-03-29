package com.multicoredump.tutorial.plumtwitter.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.application.PlumTwitterApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class MentionsFragment extends TweetTabFragment {

    public MentionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mentions, container, false);
    }

    @Override
    public Drawable getTabDrawable() {
        return ContextCompat.getDrawable(PlumTwitterApplication.getContext(), R.drawable.mention);
    }

    @Override
    public String getTabTitle() {
        return "Mentions";
    }

    @Override
    public int getTabPosition() {
        return 1;
    }
}
