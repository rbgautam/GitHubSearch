package com.forgeinnovations.android.gitrepoelite.bookmark;

import android.view.MenuItem;

import com.forgeinnovations.android.gitrepoelite.tabs.BookmarkTab;

/**
 * Created by Rahul B Gautam on 5/22/18.
 */
public interface BookmarkContract {


    void inflateMenuItems(BookmarkTab bookmarkTab);

    boolean onMenuItemClicked(MenuItem item);
}
