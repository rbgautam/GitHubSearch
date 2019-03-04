package com.forgeinnovations.android.gitrepoelite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.Item;
import com.forgeinnovations.android.gitrepoelite.utilities.NetworkUtils;

/**
 * Created by Rahul B Gautam on 5/22/18.
 */
public class GitHubSearchOpenHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "GitHubSearch.db";
    public static final int DATABASE_VERSION = 3;
    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public GitHubSearchOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL(GitHubSearchDbContract.BookmarkEntry.SQL_CREATE_TABLE);
        db.execSQL(GitHubSearchDbContract.BookmarkEntryNew.SQL_CREATE_TABLE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion < 3){
            db.execSQL(GitHubSearchDbContract.BookmarkEntryNew.SQL_CREATE_TABLE);
            db.execSQL("INSERT INTO "+GitHubSearchDbContract.BookmarkEntryNew.TABLE_NAME+" ("+GitHubSearchDbContract.BookmarkEntryNew.COLUMN_BOOKMARK_DATA+","+GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_ID +","+GitHubSearchDbContract.BookmarkEntryNew.COLUMN_KEYWORD+","+GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_URL+","+GitHubSearchDbContract.BookmarkEntryNew.COLUMN_DATATYPE + ") SELECT "+GitHubSearchDbContract.BookmarkEntry.COLUMN_BOOKMARK_DATA+","+GitHubSearchDbContract.BookmarkEntry.COLUMN_GITHUB_ID+","+GitHubSearchDbContract.BookmarkEntry.COLUMN_KEYWORD +", 'olddata' ,'SEARCHDATA' FROM "+GitHubSearchDbContract.BookmarkEntry.TABLE_NAME);
            updateBookmarksFromOldDb(db);
        }

    }

    private void updateBookmarksFromOldDb(SQLiteDatabase sqlDb) {
        final String[] bookmarkColumns = new String[]{GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_ID, GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_URL, GitHubSearchDbContract.BookmarkEntryNew.COLUMN_BOOKMARK_DATA, GitHubSearchDbContract.BookmarkEntryNew.COLUMN_KEYWORD, GitHubSearchDbContract.BookmarkEntryNew.COLUMN_DATATYPE};
        Cursor bookmarkCursor = sqlDb.query(GitHubSearchDbContract.BookmarkEntryNew.TABLE_NAME, bookmarkColumns, GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_URL+" = ?", new String[]{"olddata"}, null, null, GitHubSearchDbContract.BookmarkEntry._ID + " desc");

        int bookmarkDataPos = bookmarkCursor.getColumnIndex(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_BOOKMARK_DATA);
        int dataTypePos = bookmarkCursor.getColumnIndex(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_DATATYPE);
        int githubUrlPos = bookmarkCursor.getColumnIndex(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_URL);

        while (bookmarkCursor.moveToNext()) {

            String bookmarkDataStr = bookmarkCursor.getString(bookmarkDataPos);
            String datatype = bookmarkCursor.getString(dataTypePos);
            String githubUrl = bookmarkCursor.getString(githubUrlPos);
            Item item = null;
            com.forgeinnovations.android.gitrepoelite.datamodel.GitHubTopRepo.Item newItem = null;
            //TODO: convert string to pojo
            if (datatype.equals("SEARCHDATA") && githubUrl.equals("olddata")){
                item = NetworkUtils.ConvertFromJSON(bookmarkDataStr);

                ContentValues contentValues = new ContentValues();
                contentValues.put(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_URL,item.getHtmlUrl());
                sqlDb.update(GitHubSearchDbContract.BookmarkEntryNew.TABLE_NAME,contentValues,null,null );
            }
            //TODO: update db with item.getHtmlUrl()

        }

        bookmarkCursor.close();


    }
}
