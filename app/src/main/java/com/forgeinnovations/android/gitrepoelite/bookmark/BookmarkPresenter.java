package com.forgeinnovations.android.gitrepoelite.bookmark;

import android.view.MenuItem;

import com.forgeinnovations.android.gitrepoelite.R;
import com.forgeinnovations.android.gitrepoelite.data.GitHubBookmarkItemAdapter;
import com.forgeinnovations.android.gitrepoelite.tabs.BookmarkTab;

/**
 * Created by Rahul B Gautam on 5/22/18.
 */
public class BookmarkPresenter implements BookmarkContract {

    private static BookmarkTab mParentContext;
    private GitHubBookmarkItemAdapter mGitHubListItemAdapter;

    public BookmarkPresenter(BookmarkTab bookmarkTab, GitHubBookmarkItemAdapter gitHubListItemAdapter) {
        //this.mParentContext = bookmarkTab.getContext();
        this.mGitHubListItemAdapter = gitHubListItemAdapter;

    }

    @Override
    public void inflateMenuItems(BookmarkTab bookmarkTab) {

    }

    @Override
    public boolean onMenuItemClicked(MenuItem item) {


        int itemThatWasClickedId = item.getItemId();

        switch (itemThatWasClickedId) {

//            case R.id.action_search:
//                //Intent intent = new Intent(mParentContext, MainActivity.class);
//                //mParentContext.startActivity(intent);
//                break;
            case R.id.menu_item_share:
                shareBookmarks();
                break;

        }

        return true;

    }

    private void shareBookmarks() {


    }
}
