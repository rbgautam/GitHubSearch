package com.forgeinnovations.android.gitrepoelite.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.GitHubBookmarkResponse;
import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubTopRepo.Item;
import com.forgeinnovations.android.gitrepoelite.utilities.NetworkUtils;

import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Created by Rahul B Gautam on 5/22/18.
 */
public class DataManager {

    private static DataManager singletonInstance = null;

    private static LinkedHashMap<String, com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.Item> mBookmarks = new LinkedHashMap<>();
    private String TAG = "DATAMANAGER";

    public static DataManager getSingletonInstance() {

        if (singletonInstance == null) {

            singletonInstance = new DataManager();
        }
        return singletonInstance;
    }


    public static GitHubBookmarkResponse loadFromDatabase(GitHubSearchOpenHelper dbHelper) {

        SQLiteDatabase sqlDb = dbHelper.getReadableDatabase();
        final String[] bookmarkColumns = new String[]{GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_ID, GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_URL, GitHubSearchDbContract.BookmarkEntryNew.COLUMN_BOOKMARK_DATA, GitHubSearchDbContract.BookmarkEntryNew.COLUMN_KEYWORD, GitHubSearchDbContract.BookmarkEntryNew.COLUMN_DATATYPE};
        Cursor bookmarkCursor = sqlDb.query(GitHubSearchDbContract.BookmarkEntryNew.TABLE_NAME, bookmarkColumns, null, null, null, null, GitHubSearchDbContract.BookmarkEntry._ID + " desc");

        return loadBookmarksFromDb(bookmarkCursor);

    }



    private static GitHubBookmarkResponse loadBookmarksFromDb(Cursor bookmarkCursor) {

        int bookmarkDataPos = bookmarkCursor.getColumnIndex(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_BOOKMARK_DATA);
        int dataTypePos = bookmarkCursor.getColumnIndex(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_DATATYPE);

        DataManager dm = getSingletonInstance();

        dm.mBookmarks.clear();

        while (bookmarkCursor.moveToNext()) {

            String bookmarkDataStr = bookmarkCursor.getString(bookmarkDataPos);
            String datatype = bookmarkCursor.getString(dataTypePos);

            com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.Item item = null;
            Item newItem = null;
            //TODO: convert string to pojo
            if (datatype.equals("SEARCHDATA"))
                item = NetworkUtils.ConvertFromJSON(bookmarkDataStr);

            if (datatype.equals("TOPREPODATA")) {
                newItem = NetworkUtils.ConvertFromTopRepoJSON(bookmarkDataStr);
                item = newItem.convertToSerachItem(newItem);
            }

            if(item!= null)
                mBookmarks.put(item.getHtmlUrl(), item);
            //mBookmarks.add(item);

        }

        bookmarkCursor.close();

        GitHubBookmarkResponse result = new GitHubBookmarkResponse();

        result.setBookmarkItems(mBookmarks);
        //result.set (mBookmarks.size());

        return result;
    }

    public static long saveBookmark(GitHubSearchOpenHelper dbHelper, String data, String key, String keyword) {

        long result = 0;
        DataManager dm = getSingletonInstance();

        SQLiteDatabase sqlDb = dbHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_ID, key);
        initialValues.put(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_BOOKMARK_DATA, data);
        initialValues.put(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_KEYWORD, keyword);
        initialValues.put(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_URL, "http://localhost");
        initialValues.put(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_DATATYPE, "SEARCHDATA");


        try {
            result = sqlDb.insertWithOnConflict(GitHubSearchDbContract.BookmarkEntryNew.TABLE_NAME, null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
        } catch (Exception ex) {

        }

        sqlDb.close();

        return result;


    }

    public static long saveRepoBookmark(GitHubSearchOpenHelper dbHelper, String data, String key, String keyword, Integer gitHubId, String dataType) {

        long result = 0;
        DataManager dm = getSingletonInstance();

        SQLiteDatabase sqlDb = dbHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_ID, gitHubId);
        initialValues.put(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_URL, key);
        initialValues.put(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_BOOKMARK_DATA, data);
        initialValues.put(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_KEYWORD, keyword);
        initialValues.put(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_DATATYPE, dataType);

        try {
            result = sqlDb.insertWithOnConflict(GitHubSearchDbContract.BookmarkEntryNew.TABLE_NAME, null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
        } catch (Exception ex) {
            String message = ex.getMessage();
        }

        sqlDb.close();

        return result;


    }

    public HashSet<String> getBookmarks(GitHubSearchOpenHelper dbHelper, String dataType) {

        DataManager dm = getSingletonInstance();
        HashSet<String> itemList = new HashSet<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.query(GitHubSearchDbContract.BookmarkEntryNew.TABLE_NAME, new String[]{GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_URL}, GitHubSearchDbContract.BookmarkEntryNew.COLUMN_DATATYPE + "=?", new String[]{dataType}, null, null, null);

            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_URL);
                String itemUrl = cursor.getString(index);
                itemList.add(itemUrl);
            }

        } catch (Exception ex) {

        }
        return itemList;
    }

    public void deleteBookmark(GitHubSearchOpenHelper dbHelper, String id) {

        DataManager dm = getSingletonInstance();

        SQLiteDatabase sqlDb = dbHelper.getWritableDatabase();

        try {

            String whereClause = GitHubSearchDbContract.BookmarkEntryNew.COLUMN_GITHUB_URL + "=?";

            sqlDb.delete(GitHubSearchDbContract.BookmarkEntryNew.TABLE_NAME, whereClause, new String[]{id});
        } catch (Exception ex) {
            Log.e(TAG,"Delete failed : "+ ex.getMessage());

        }
    }
}
