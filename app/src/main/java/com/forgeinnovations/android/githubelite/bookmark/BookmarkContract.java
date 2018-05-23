package com.forgeinnovations.android.githubelite.bookmark;

import android.view.MenuItem; /**
 * Created by Rahul B Gautam on 5/22/18.
 */
public interface BookmarkContract {


    void inflateMenuItems(BookmarkActivity bookmarkActivity);

    boolean onMenuItemClicked(MenuItem item);
}
