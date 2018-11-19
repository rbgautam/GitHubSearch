package com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch;

import java.util.LinkedHashMap;

/**
 * Created by Rahul B Gautam on 7/14/18.
 */
public class GitHubBookmarkResponse {
    private LinkedHashMap<String, Item> bookmarkItems = new LinkedHashMap<>();

    public Integer getBookmarkCount() {
        return getBookmarkItems().size();
    }




    public LinkedHashMap<String, Item> getBookmarkItems() {
        return bookmarkItems;
    }

    public void setBookmarkItems(LinkedHashMap<String, Item> bookmarkItems) {
        this.bookmarkItems = bookmarkItems;
    }


}