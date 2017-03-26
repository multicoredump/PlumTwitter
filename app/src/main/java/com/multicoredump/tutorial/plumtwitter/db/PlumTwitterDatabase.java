package com.multicoredump.tutorial.plumtwitter.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by radhikak on 3/25/17.
 */


@Database(name = PlumTwitterDatabase.NAME, version = PlumTwitterDatabase.VERSION)
public class PlumTwitterDatabase {

    public static final String NAME = "ToDoListAppDatabase";

    public static final int VERSION = 2;
}