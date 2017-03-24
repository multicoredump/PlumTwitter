package com.multicoredump.tutorial.plumtwitter.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by radhikak on 3/23/17.
 */

public class DateFormatting {

    public static String getRelativeTime(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeTime = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeTime = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  relativeTime.replace(" ago", "").replace(" hours", "h").replace(" hour", "h")
                .replace(" seconds", "s")
                .replace(" minutes", "m").replace(" minute", "m")
                .replace(" day", "d").replace(" days", "d");

    }
}
