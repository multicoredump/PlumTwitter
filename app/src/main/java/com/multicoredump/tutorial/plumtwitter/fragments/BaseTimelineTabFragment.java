package com.multicoredump.tutorial.plumtwitter.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.multicoredump.tutorial.plumtwitter.R;
import com.multicoredump.tutorial.plumtwitter.adapter.TweetAdapter;
import com.multicoredump.tutorial.plumtwitter.adapter.TweetFragmentPagerAdapater;
import com.multicoredump.tutorial.plumtwitter.application.PlumTwitterApplication;
import com.multicoredump.tutorial.plumtwitter.model.Tweet;
import com.multicoredump.tutorial.plumtwitter.twitter.OnReplyActionListener;
import com.multicoredump.tutorial.plumtwitter.twitter.TwitterRestClient;
import com.multicoredump.tutorial.plumtwitter.utils.EndlessRecyclerViewScrollListener;
import com.multicoredump.tutorial.plumtwitter.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by radhikak on 3/29/17.
 */

public abstract class BaseTimelineTabFragment extends Fragment implements TweetFragmentPagerAdapater.TweetPagerTab, OnReplyActionListener {

    private static final String TAG = TimelineFragment.class.getName();

    protected TwitterRestClient twitterClient = PlumTwitterApplication.getTwitterClient();

    protected  ArrayList<Tweet> tweets;
    protected TweetAdapter tweetAdapter;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

    @BindView(R.id.rvTweets)
    RecyclerView rvTweets;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    protected EndlessRecyclerViewScrollListener scrollListener;
    protected LinearLayoutManager mLayoutManager;

    public BaseTimelineTabFragment() {
        // Required empty public constructor
        tweets = new ArrayList<>();
        tweetAdapter = new TweetAdapter(tweets, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, view);

        Log.d(TAG, "Inside onViewCreated");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTweets.setAdapter(tweetAdapter);
        rvTweets.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(mLayoutManager);

        //Recylerview decorater
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
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
//                    handleRequestError();
                }

                updateTimeline(0);
            }
        });

        updateTimeline(0);

    }

    private long getMaxId() {
        return tweets.get(tweets.size() - 1).getId();
    }

    protected abstract void updateTimeline(long id);

    @Override
    public void onReply(Tweet tweet) {
    }
}
