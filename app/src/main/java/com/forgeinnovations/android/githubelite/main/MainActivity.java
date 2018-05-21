/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.forgeinnovations.android.githubelite.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.data.GitHubListItemAdapter;
import com.forgeinnovations.android.githubelite.data.GitHubRestAdapter;
import com.forgeinnovations.android.githubelite.datamodel.GitHubSeachResponse;

public class MainActivity extends AppCompatActivity implements MainView {

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    private TextView mSearchResultsTextView;

    // TODO (12) Create a variable to store a reference to the error message TextView
    private TextView mErrorMessageTextView;


    // TODO (24) Create a ProgressBar variable to store a reference to the ProgressBar
    private ProgressBar mDownloadRequestProgressBar;

    //GitHubRestAdapter queryAdapter = new GitHubRestAdapter();

    private MainPresenter mPresenter;

    private GitHubListItemAdapter mGitHubListItemAdapter;
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    //TODO:S
    //TODO: completed Add MVP pattern
    //TODO: completed : Add retofit to parse data
    //TODO: completed ADD cards to display data
    //TODO: completed Add details if click on card
    //TODO: completed Navigate to Website
    //TODO: Share link
    //TODO: Add loader manager AsyncTaskLoader
    //TODO: completed Fix appname and Icon - HitHub elite
    //TODO: Fix issue when no network
    //TODO:completed fix packagename

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_github);



        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        //mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
        // TODO (13) Get a reference to the error TextView using findViewById
        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);

        // TODO (25) Get a reference to the ProgressBar using findViewById
        mDownloadRequestProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(linearLayoutManager);

        mGitHubListItemAdapter = new GitHubListItemAdapter(this);

        mRecyclerView.setAdapter(mGitHubListItemAdapter);


        mPresenter = new MainPresenter(this, new GitHubRestAdapter(),mGitHubListItemAdapter);

    }

    @Override
    public void showResultsTextView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideResultsTextView() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        Toast.makeText(this,R.string.error_message,Toast.LENGTH_LONG);
        showErrorMessageTextView();

    }

    @Override
    public void showErrorMessageTextView() {
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorMessageTextView() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);

    }

    /**
     * Makes the progressbar VISIBLE
     */
    @Override
    public void showProgress() {
        mDownloadRequestProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Makes the progressbar INVISIBLE
     */
    @Override
    public void hideProgress() {
        mDownloadRequestProgressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Sets the text in the ResultsTextView
     */
    @Override
    public void setResultsTextView(String text) {
        mSearchResultsTextView.setText(text);
    }

    /**
     * Sets the error message
     */
    @Override
    public void setErrorMessageTextView(int text) {
        mErrorMessageTextView.setText(text);
        hideResultsTextView();

    }

    /**
     * @return text from edittext
     */
    @Override
    public String getSearchStringEditText() {
        return mSearchBoxEditText.getText().toString();
    }

    @Override
    public void setUrlDisplayTextView(String text) {
        mUrlDisplayTextView.setText(text);

    }

    // TODO (2) Create a method called makeGithubSearchQuery
    // TODO (3) Within this method, build the URL with the text from the EditText and set the built URL to the TextView
    private void makeGithubSearchQuery() {

        String searchStr = getSearchStringEditText();

        mPresenter.makeGithubSearchQuery(searchStr);
    }


    // TODO (14) Create a method called showJsonDataView to show the data and hide the error
    private void showJsonDataView(GitHubSeachResponse githubSearchResults) {

        mPresenter.showJsonDataView(githubSearchResults);
        mRecyclerView.setVisibility(View.VISIBLE);

    }

    // TODO (15) Create a method called showErrorMessage to show the error and hide the data
    public void showErrorMessage() {
        setErrorMessageTextView(R.string.error_message);
        hideResultsTextView();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.onMenuItemClicked(item);
        Context context = getApplicationContext();
        mPresenter.hideKeyboard(MainActivity.this);
        return super.onOptionsItemSelected(item);
    }


}
