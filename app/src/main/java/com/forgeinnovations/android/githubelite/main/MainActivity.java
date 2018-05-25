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

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.data.GitHubListItemAdapter;
import com.forgeinnovations.android.githubelite.data.GitHubRestAdapter;
import com.forgeinnovations.android.githubelite.datamodel.GitHubSeachResponse;
import com.forgeinnovations.android.githubelite.db.GitHubSearchOpenHelper;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

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

    private GitHubSearchOpenHelper mDbOpenHelper;
    private ImageButton mSearchButton;
    public String mKeyword;
    private ShowcaseView showcaseView;
    private int counter = 0;
    private MenuItem mBookmarkMenuitem;


    //TODO:S
    //TODO: completed Add MVP pattern
    //TODO: completed : Add retofit to parse data
    //TODO: completed ADD cards to display data
    //TODO: completed Add details if click on card
    //TODO: completed Navigate to Website
    //TODO: completed Share link
    //TODO: completed Add loader manager AsyncTaskLoader
    //TODO: completed Fix appname and Icon - HitHub elite
    //TODO: completed Fix issue when no network
    //TODO:completed fix packagename

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mDbOpenHelper = new GitHubSearchOpenHelper(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_github);


        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        //mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
        // TODO (13) Get a reference to the error TextView using findViewById
        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);

        // TODO (25) Get a reference to the ProgressBar using findViewById
        mDownloadRequestProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(linearLayoutManager);

        mGitHubListItemAdapter = new GitHubListItemAdapter(this);

        mRecyclerView.setAdapter(mGitHubListItemAdapter);


        mPresenter = new MainPresenter(this, new GitHubRestAdapter(), mGitHubListItemAdapter);


        handleIntent(getIntent());




    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }


    /**
     * Handling intent data
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            mPresenter.makeGithubSearchQuery(query);

//            //First time help
//
//            showcaseView = new ShowcaseView.Builder(this)
//                    .setTarget(Target.NONE)
//                    .setContentTitle("Get Started")
//                    .setContentText("View all-time greatest repositories\nClick on the search icon and type your keyword")
//                    .setOnClickListener(this)
//                    .build();
//
//
//            setAlpha(0.2f, mRecyclerView);
//            showcaseView.setButtonText(getString(R.string.next));

        }

    }


    private View.OnClickListener searchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //makeGithubSearchQuery();
        }
    };


    @Override
    protected void onDestroy() {
        //mDbOpenHelper.getWritableDatabase();
        mDbOpenHelper.close();
        super.onDestroy();
    }

    @Override
    public void showResultsTextView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideResultsTextView() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_LONG);
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


    @Override
    public void setUrlDisplayTextView(String text) {
        mUrlDisplayTextView.setText(text);

    }

    // TODO (2) Create a method called makeGithubSearchQuery
    // TODO (3) Within this method, build the URL with the text from the EditText and set the built URL to the TextView
    private void makeGithubSearchQuery() {

        //String searchStr = getSearchStringEditText();

        //mPresenter.makeGithubSearchQuery(searchStr);
    }


    // TODO (15) Create a method called showErrorMessage to show the error and hide the data
    public void showErrorMessage() {
        setErrorMessageTextView(R.string.error_message);
        hideResultsTextView();

    }

    /**
     * @return text from edittext
     */
    @Override
    public String getSearchStringEditText() {
        return mKeyword;
    }

    /**
     * @return text from edittext
     */
    @Override
    public void setSearchStringEditText(String queryStr) {
        mKeyword = queryStr;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_widget).getActionView();


        searchView.setOnQueryTextListener(createSearchQueryListener());

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    /**
     * Creates and returns a listener, which allows to start a search query when the user enters
     * text.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * OnQueryTextListener}
     */
    public SearchView.OnQueryTextListener createSearchQueryListener() {
        return new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                setSearchStringEditText(query);
                mPresenter.makeGithubSearchQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String query) {

                return false;
            }

        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.onMenuItemClicked(item);

        mPresenter.hideKeyboard(MainActivity.this);
        return super.onOptionsItemSelected(item);
    }

    private void setAlpha(float alpha, View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            for (View view : views) {
                view.setAlpha(alpha);
            }
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (counter) {
//            case 0:
//                showcaseView.setTarget(Target.NONE);
//                showcaseView.setContentTitle("See your bookmarked repositories.");
//                showcaseView.setContentText("Click on the bookmark icon");
//                showcaseView.setButtonText(getString(R.string.close));
//                setAlpha(0.2f,mRecyclerView);
//                break;
//            case 1:
//                showcaseView.hide();
//                setAlpha(1.0f,mRecyclerView);
//                break;
////
//            case 2:
//                showcaseView.setTarget(Target.NONE);
//                showcaseView.setContentTitle("Check it out");
//                showcaseView.setContentText("You don't always need a target to showcase");
//                showcaseView.setButtonText(getString(R.string.close));
//                setAlpha(0.4f, textView1, textView2, textView3);
//                break;
//
//            case 3:
//                showcaseView.hide();
//                setAlpha(1.0f, textView1, textView2, textView3);
//                break;
        }
        counter++;
    }
}
