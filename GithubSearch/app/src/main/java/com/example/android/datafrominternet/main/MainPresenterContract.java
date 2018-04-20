package com.example.android.datafrominternet.main;

import android.view.MenuItem;

import com.example.android.datafrominternet.datamodel.Item;

import java.util.List;

/**
 * Created by Rahul B Gautam on 4/18/18.
 */
public interface MainPresenterContract {
    /**
     * Calls the AsyncTask for N/W call
     */
    public void makeGithubSearchQuery(String searchStr);

    /**
     * Returns the data to be displayed on the results textView
     * @param githubSearchResults
     */
    public void showJsonDataView(List<Item> githubSearchResults);

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


}
