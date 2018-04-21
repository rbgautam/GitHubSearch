package com.example.android.datafrominternet.data;

import android.os.AsyncTask;

import com.example.android.datafrominternet.datamodel.GitHubSeachResponse;
import com.example.android.datafrominternet.main.MainPresenter;

/**
 * Created by Rahul B Gautam on 4/20/18.
 */
public class GitHubSearchQuery extends AsyncTask<String, Void, GitHubSeachResponse> implements GitHubRestAdapter.AsyncWebResponse {
    private GitHubSeachResponse mGitHubSeachResponse;
    public GitHubRestAdapter.AsyncWebResponse delegate = null;

    GitHubRestAdapter mQueryAdapter;
    MainPresenter mMainPresenter;

    public GitHubSearchQuery(GitHubRestAdapter queryAdapter, MainPresenter mainPresenter) {
        this.mQueryAdapter = queryAdapter;
        this.mMainPresenter = mainPresenter;
    }

    @Override
    protected void onPreExecute() {
        mMainPresenter.showProgress();
    }

    @Override
    protected GitHubSeachResponse doInBackground(String... strings) {

        //GitHubRestAdapter queryAdapter = new GitHubRestAdapter();
        mQueryAdapter.delegate = this;

        mQueryAdapter.getGitHubData(strings[0]);
        return mGitHubSeachResponse;

    }

    @Override
    protected void onPostExecute(GitHubSeachResponse githubSearchResults) {

    }


    @Override
    public void processFinish(GitHubSeachResponse githubSearchResults) {

        // TODO (27) As soon as the loading is complete, hide the loading indicator
        mMainPresenter.hideProgress();
        if (githubSearchResults != null && !(githubSearchResults.getItems().size() == 0)) {
            // TODO (17) Call showJsonDataView if we have valid, non-null results
            mMainPresenter.showJsonDataView(githubSearchResults);

        } else {
            mMainPresenter.showErrorMessage();
        }
    }
}