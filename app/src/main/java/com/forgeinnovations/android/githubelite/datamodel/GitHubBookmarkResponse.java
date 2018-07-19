package com.forgeinnovations.android.githubelite.datamodel;

import java.util.LinkedHashMap;

/**
 * Created by Rahul B Gautam on 7/14/18.
 */
public class GitHubBookmarkResponse {
    private LinkedHashMap<Integer, Item> bookmarkItems = new LinkedHashMap<>();

    public Integer getBookmarkCount() {
        return getBookmarkItems().size();
    }




    public LinkedHashMap<Integer, Item> getBookmarkItems() {
        return bookmarkItems;
    }

    public void setBookmarkItems(LinkedHashMap<Integer, Item> bookmarkItems) {
        this.bookmarkItems = bookmarkItems;
    }


}