package com.forgeinnovations.android.githubelite.bookmark;

import android.content.Intent;
import android.view.MenuItem;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.data.GitHubBookmarkItemAdapter;
import com.forgeinnovations.android.githubelite.main.MainActivity;

/**
 * Created by Rahul B Gautam on 5/22/18.
 */
public class BookmarkPresenter implements BookmarkContract {

    private static BookmarkActivity mParentContext;
    private GitHubBookmarkItemAdapter mGitHubListItemAdapter;

    public BookmarkPresenter(BookmarkActivity bookmarkActivity, GitHubBookmarkItemAdapter gitHubListItemAdapter) {
        this.mParentContext = bookmarkActivity;
        this.mGitHubListItemAdapter = gitHubListItemAdapter;

    }

    @Override
    public void inflateMenuItems(BookmarkActivity bookmarkActivity) {

    }

    @Override
    public boolean onMenuItemClicked(MenuItem item) {


        int itemThatWasClickedId = item.getItemId();

        switch (itemThatWasClickedId) {

            case R.id.action_search:
                Intent intent = new Intent(mParentContext, MainActivity.class);
                mParentContext.startActivity(intent);
                break;
            case R.id.action_bookmark:
                shareBookmarks();
                break;

        }

        return true;

    }

    private void shareBookmarks() {


    }
}
