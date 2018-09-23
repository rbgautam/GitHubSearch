package com.forgeinnovations.android.githubelite.data;

import android.os.AsyncTask;

import com.forgeinnovations.android.githubelite.datamodel.GitHubSearch.GitHubSeachResponse;
import com.forgeinnovations.android.githubelite.datamodel.GitHubSearch.Item;
import com.forgeinnovations.android.githubelite.db.DataManager;
import com.forgeinnovations.android.githubelite.db.GitHubSearchOpenHelper;
import com.forgeinnovations.android.githubelite.main.MainPresenter;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Rahul B Gautam on 4/20/18.
 */
public class GitHubSearchQuery extends AsyncTask<String, Void, GitHubSeachResponse> implements GitHubRestAdapter.AsyncWebResponse {
    private GitHubSeachResponse mGitHubSeachResponse;
    public GitHubRestAdapter.AsyncWebResponse delegate = null;

    GitHubRestAdapter mQueryAdapter;
    MainPresenter mMainPresenter;
    private GitHubSearchOpenHelper mDbOpenHelper;
    private HashSet<String> mBookmarkList;

    public GitHubSearchQuery(GitHubRestAdapter queryAdapter, MainPresenter mainPresenter, GitHubSearchOpenHelper dbOpenHelper) {
        this.mQueryAdapter = queryAdapter;
        this.mMainPresenter = mainPresenter;
        this.mDbOpenHelper = dbOpenHelper;
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
        mMainPresenter.mKeyword = strings[0];
        return mGitHubSeachResponse;

    }

    @Override
    protected void onPostExecute(GitHubSeachResponse githubSearchResults) {
        DataManager dm = DataManager.getSingletonInstance();
        mBookmarkList = dm.getBookmarks(mDbOpenHelper,"SEARCHDATA");

    }


    @Override
    public void processFinish(GitHubSeachResponse githubSearchResults) {

        // TODO (27) As soon as the loading is complete, hide the loading indicator
        mMainPresenter.hideProgress();
        if (githubSearchResults.getItems() != null ) {
            HashSet<String> favList = mBookmarkList;

            updateFavList(favList,githubSearchResults.getItems());
            String keyword = mMainPresenter.mKeyword;
            // TODO (17) Call showJsonDataView if we have valid, non-null results
            mMainPresenter.showJsonDataView(githubSearchResults,keyword);

        } else {
            mMainPresenter.showErrorMessage();
        }
    }

    private void updateFavList(HashSet<String> favList, List<Item> list){

        for (Item item:list) {

            if( favList.contains(item.getHtmlUrl())){
                item.setFavorite(true);
            }

        }
    }
}
