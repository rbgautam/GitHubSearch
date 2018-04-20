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
package com.example.android.datafrominternet.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.datafrominternet.R;
import com.example.android.datafrominternet.data.GitHubRestAdapter;
import com.example.android.datafrominternet.datamodel.GitHubSeachResponse;
import com.example.android.datafrominternet.datamodel.Item;

import java.util.List;

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

    //TODO:S
    //TODO: completed Add MVP pattern
    //TODO: completed : Add retofit to parse data
    //TODO: ADD cards to display data
    //TODO: Add details if click on card
    //TODO: Navigate to Website
    //TODO: Share link
    //TODO: Add loader manager AsyncTaskLoader

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
        // TODO (13) Get a reference to the error TextView using findViewById
        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);

        // TODO (25) Get a reference to the ProgressBar using findViewById
        mDownloadRequestProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mPresenter = new MainPresenter(this, new GitHubRestAdapter());

    }

    @Override
    public void showResultsTextView() {
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideResultsTextView() {
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
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
    private void showJsonDataView(List<Item> githubSearchResults) {

        mPresenter.showJsonDataView(githubSearchResults);

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
        return super.onOptionsItemSelected(item);
    }
}
