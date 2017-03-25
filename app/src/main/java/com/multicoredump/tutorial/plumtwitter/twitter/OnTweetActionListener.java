package com.multicoredump.tutorial.plumtwitter.twitter;

import com.multicoredump.tutorial.plumtwitter.model.Tweet;

/**
 * Created by radhikak on 3/25/17.
 */

public interface OnTweetActionListener {

    public void onRetweet(Tweet tweet);
    public void onFavorite(Tweet tweet);
    public void onReply(Tweet tweet);
}
