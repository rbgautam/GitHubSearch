package com.forgeinnovations.android.githubelite.db;

import android.provider.BaseColumns;

/**
 * Created by Rahul B Gautam on 5/21/18.
 */
public final class GitHubSearchDbContract {

    public static final class BookmarkEntry implements BaseColumns {

        public static final String TABLE_NAME = "GithubSearchBookmark";
        public static final String COLUMN_GITHUB_ID = "githubId";
        public static final String COLUMN_BOOKMARK_DATA = "bookmark_data";
        public static final String COLUMN_KEYWORD = "keyword";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + "INTEGER PRIMARY KEY ," +
        COLUMN_GITHUB_ID + "INTEGER UNIQUE NOT NULL, "+ COLUMN_BOOKMARK_DATA + " TEXT NOT NULL " +COLUMN_KEYWORD +" TEXT NOT NULL " + ")";


    }




}
