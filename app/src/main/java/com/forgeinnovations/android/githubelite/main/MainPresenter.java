package com.forgeinnovations.android.githubelite.main;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.data.GitHubListItemAdapter;
import com.forgeinnovations.android.githubelite.data.GitHubRestAdapter;
import com.forgeinnovations.android.githubelite.data.GitHubSearchQuery;
import com.forgeinnovations.android.githubelite.datamodel.GitHubSeachResponse;
import com.forgeinnovations.android.githubelite.datamodel.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rahul B Gautam on 4/18/18.
 */
public class MainPresenter implements MainPresenterContract {

    private MainView mMainView;

    //TODO: completed : add model instance

    private GitHubRestAdapter mGitHubRestAdapter;
    private GitHubListItemAdapter mGitHubListItemAdapter;

    private Map<Integer, Item> itemMap = new HashMap<Integer, Item>();

    public MainPresenter(MainView mMainView, GitHubRestAdapter mGitHubRestAdapter, GitHubListItemAdapter gitHubListItemAdapter) {
        this.mMainView = mMainView;
        this.mGitHubRestAdapter = mGitHubRestAdapter;
        this.mGitHubListItemAdapter = gitHubListItemAdapter;
    }

    /**
     * Calls the AsyncTask for N/W call
     */
    @Override
    public void makeGithubSearchQuery(String searchStr) {

        if (!searchStr.isEmpty()) {

            GitHubSearchQuery repoAsychQuery = new GitHubSearchQuery(mGitHubRestAdapter,this);

            repoAsychQuery.execute(searchStr);

        }

    }

    /**
     * Returns the data to be displayed on the results textView
     *
     * @param githubSearchResults
     */
    @Override
    public void showJsonDataView(GitHubSeachResponse githubSearchResults) {
        StringBuilder strBuilder = new StringBuilder();

        mMainView.setUrlDisplayTextView(mGitHubRestAdapter.urlString);

        try
        {

            mGitHubListItemAdapter.setGitHubData(githubSearchResults);
//            for (Item item : githubSearchResults) {
//                String formattedLink = "\n" + item.getDescription() + "\n" + item.getHtmlUrl() + "\n";
//                strBuilder.append(formattedLink);
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        mMainView.showResultsTextView();
//        mMainView.setResultsTextView(strBuilder.toString());
        mMainView.hideErrorMessageTextView();
    }

    /**
     * Makes the progressbar VISIBLE
     */
    @Override
    public void showProgress() {
        mMainView.showProgress();
    }

    /**
     * Makes the progressbar INVISIBLE
     */
    @Override
    public void hideProgress() {
        mMainView.hideProgress();
    }

    /**
     * Shows an error message
     */
    @Override
    public void showErrorMessage() {
        mMainView.showErrorMessage();
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemClicked(MenuItem item) {

        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            // TODO (4) Remove the Toast message when the search menu item is clicked
            // TODO (5) Call makeGithubSearchQuery when the search menu item is clicked

            makeGithubSearchQuery(mMainView.getSearchStringEditText());
            return true;
        }
        return false;
    }


    /**
     * Hide the soft keyboard
     */
    @Override
    public void hideKeyboard(Activity activity){


        InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        try {

            inputManager.hideSoftInputFromWindow((null == activity.getCurrentFocus()) ? null : activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        catch (Exception ex){
            Log.e("Mainactivity","Cannot close keyboard");
        }

    }

    @Override
    public void setItemMap(List<Item> items) {
        itemMap.clear();
        for (Item item:items) {
            itemMap.put(item.getId(),item);
        }
    }

    @Override
    public Item getItemById(Integer id){

        if(itemMap.containsKey(id))
            return itemMap.get(id);
        else
            return new Item();

    }


}
