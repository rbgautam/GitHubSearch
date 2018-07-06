package com.forgeinnovations.android.githubelite.bookmark;

import android.view.MenuItem;

import com.forgeinnovations.android.githubelite.tabs.BookmarkTab;

/**
 * Created by Rahul B Gautam on 5/22/18.
 */
public interface BookmarkContract {


    void inflateMenuItems(BookmarkTab bookmarkTab);

    boolean onMenuItemClicked(MenuItem item);
}
