package com.multicoredump.tutorial.plumtwitter.twitter;

import com.multicoredump.tutorial.plumtwitter.model.Tweet;

/**
 * Created by radhikak on 3/24/17.
 */

public interface OnTweetActionListerner {

    public void onRetweet(Tweet tweet);
    public void onFavorite(Tweet tweet);
    public void onReply(Tweet tweet);

}
