package com.example.android.datafrominternet.main;

import android.view.MenuItem;

import com.example.android.datafrominternet.R;
import com.example.android.datafrominternet.data.GitHubListItemAdapter;
import com.example.android.datafrominternet.data.GitHubRestAdapter;
import com.example.android.datafrominternet.data.GitHubSearchQuery;
import com.example.android.datafrominternet.datamodel.GitHubSeachResponse;

/**
 * Created by Rahul B Gautam on 4/18/18.
 */
public class MainPresenter implements MainPresenterContract {

    private MainView mMainView;

    //TODO: completed : add model instance

    private GitHubRestAdapter mGitHubRestAdapter;
    private GitHubListItemAdapter mGitHubListItemAdapter;


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

//        mMainView.showResultsTextView();
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


}
