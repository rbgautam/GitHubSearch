package com.forgeinnovations.android.gitrepoelite.main;

import android.app.Activity;
import android.view.MenuItem;

import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.GitHubSeachResponse;
import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.Item;
import com.forgeinnovations.android.gitrepoelite.db.GitHubSearchOpenHelper;

import java.util.List;

/**
 * Created by Rahul B Gautam on 4/18/18.
 */
public interface MainPresenterContract {
    /**
     * Calls the AsyncTask for N/W call
     */
    public void makeGithubSearchQuery(String searchStr, GitHubSearchOpenHelper mDbOpenHelper);

    /**
     * Returns the data to be displayed on the results textView
     * @param githubSearchResults
     */
    public void showJsonDataView(GitHubSeachResponse githubSearchResults, String keyword);

    /**
     * Makes the progressbar VISIBLE
     */
    public void showProgress();

    /**
     * Makes the progressbar INVISIBLE
     */
    public void hideProgress();

    /**
     * Shows an error message
     */
    public void showErrorMessage();

    /**
     *
     * @param item
     * @return
     */
    public boolean onMenuItemClicked(MenuItem item);

    public void hideKeyboard(Activity activity);


    public void setItemMap(List<Item> items);

    public Item getItemById(Integer id);


}
