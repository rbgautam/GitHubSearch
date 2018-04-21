package com.forgeinnovations.android.githubelite.data;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.forgeinnovations.android.githubelite.datamodel.GitHubSeachResponse;
import com.forgeinnovations.android.githubelite.main.MainPresenter;

/**
 * Created by Rahul B Gautam on 4/20/18.
 */
public class GithubSearchLoader implements LoaderManager.LoaderCallbacks<GitHubSeachResponse> {

    GitHubRestAdapter mQueryAdapter;
    MainPresenter mMainPresenter;
    Context mContext;

    public GithubSearchLoader(GitHubRestAdapter mQueryAdapter, MainPresenter mMainPresenter, Context context) {
        this.mQueryAdapter = mQueryAdapter;
        this.mMainPresenter = mMainPresenter;
        this.mContext = context;
    }

    @Override
    public Loader<GitHubSeachResponse> onCreateLoader(int i, Bundle bundle) {


        return new AsyncTaskLoader<GitHubSeachResponse>(mContext) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
            }

            @Override
            public GitHubSeachResponse loadInBackground() {
                return null;
            }
        };


    }

    @Override
    public void onLoadFinished(Loader<GitHubSeachResponse> loader, GitHubSeachResponse gitHubSeachResponse) {

    }

    @Override
    public void onLoaderReset(Loader<GitHubSeachResponse> loader) {

    }
}
