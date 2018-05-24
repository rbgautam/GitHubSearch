package com.forgeinnovations.android.githubelite.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.forgeinnovations.android.githubelite.datamodel.GitHubSeachResponse;
import com.forgeinnovations.android.githubelite.datamodel.Item;
import com.forgeinnovations.android.githubelite.utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul B Gautam on 5/22/18.
 */
public class DataManager {

    private static DataManager singletonInstance  = null;

    private static List<Item> mBookmarks = new ArrayList<Item>();

    public static DataManager getSingletonInstance(){

        if(singletonInstance == null){

            singletonInstance = new DataManager();
        }
        return singletonInstance;
    }


    public static GitHubSeachResponse loadFromDatabase(GitHubSearchOpenHelper dbHelper){

        SQLiteDatabase sqlDb  = dbHelper.getReadableDatabase();
        final String[] bookmarkColumns = new String[] {GitHubSearchDbContract.BookmarkEntry.COLUMN_GITHUB_ID, GitHubSearchDbContract.BookmarkEntry.COLUMN_BOOKMARK_DATA, GitHubSearchDbContract.BookmarkEntry.COLUMN_KEYWORD};
        Cursor bookmarkCursor = sqlDb.query(GitHubSearchDbContract.BookmarkEntry.TABLE_NAME, bookmarkColumns, null, null, null, null, GitHubSearchDbContract.BookmarkEntry._ID + " desc");

        return loadBookmarksFromDb(bookmarkCursor);

    }

    private static GitHubSeachResponse loadBookmarksFromDb(Cursor bookmarkCursor) {

        int bookmarkDataPos = bookmarkCursor.getColumnIndex(GitHubSearchDbContract.BookmarkEntry.COLUMN_BOOKMARK_DATA);

        DataManager dm = getSingletonInstance();

        dm.mBookmarks.clear();

        while (bookmarkCursor.moveToNext()){

            String bookmarkDataStr = bookmarkCursor.getString(bookmarkDataPos);

            //TODO: convert string to pojo
            Item item = NetworkUtils.ConvertFromJSON(bookmarkDataStr);

            mBookmarks.add(item);

        }

        bookmarkCursor.close();

        GitHubSeachResponse result = new GitHubSeachResponse();

        result.setItems(mBookmarks);
        result.setTotalCount(mBookmarks.size());

        return result;
    }

    public static void saveBookmark(GitHubSearchOpenHelper dbHelper,String data, String key, String keyword){

        DataManager dm = getSingletonInstance();

        SQLiteDatabase sqlDb  = dbHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(GitHubSearchDbContract.BookmarkEntry.COLUMN_GITHUB_ID, key);
        initialValues.put(GitHubSearchDbContract.BookmarkEntry.COLUMN_BOOKMARK_DATA, data);
        initialValues.put(GitHubSearchDbContract.BookmarkEntry.COLUMN_KEYWORD,keyword);

        try {
            sqlDb.insertWithOnConflict  (GitHubSearchDbContract.BookmarkEntry.TABLE_NAME,null,initialValues,SQLiteDatabase.CONFLICT_IGNORE);
        }
        catch (Exception ex){

        }

        sqlDb.close();



    }

    public List<Integer> getBookmarks(GitHubSearchOpenHelper dbHelper){

        DataManager dm = getSingletonInstance();
        List<Integer> itemList = new ArrayList<Integer>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.query(GitHubSearchDbContract.BookmarkEntry.TABLE_NAME, new String[]{GitHubSearchDbContract.BookmarkEntry.COLUMN_GITHUB_ID}, null, null, null, null, null);

            while (cursor.moveToNext()){
                int index = cursor.getColumnIndex(GitHubSearchDbContract.BookmarkEntry.COLUMN_GITHUB_ID);
                Integer item = cursor.getInt(index);
                itemList.add(item);
            }

        }
        catch (Exception ex){

        }
        return itemList;
    }

    public void deleteBookmark(GitHubSearchOpenHelper dbHelper, String id) {

        DataManager dm = getSingletonInstance();

        SQLiteDatabase sqlDb = dbHelper.getWritableDatabase();

        try{

            String whereClause =  GitHubSearchDbContract.BookmarkEntry.COLUMN_GITHUB_ID +  "=?";

            sqlDb.delete(GitHubSearchDbContract.BookmarkEntry.TABLE_NAME,whereClause,new String[]{id});
        }
        catch (Exception ex){


        }
    }
}
