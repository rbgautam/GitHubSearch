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
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.data.GitHubListItemAdapter;

public class MainActivity extends AppCompatActivity {


    private MainPresenter mPresenter;

    private GitHubListItemAdapter mGitHubListItemAdapter;
    private RecyclerView mRecyclerView;
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


        //mPresenter = new MainPresenter(this, new GitHubRestAdapter(), mGitHubListItemAdapter);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsMainFragment fragment = new SlidingTabsMainFragment();
            transaction.replace(R.id.main_content_fragment, fragment);
            transaction.commit();
        }


//        mGitHubListItemAdapter = new GitHubListItemAdapter(this);
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_github);
//        mRecyclerView.setAdapter(mGitHubListItemAdapter);

        //createSearchQueryListener

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


    // TODO (2) Create a method called makeGithubSearchQuery
    // TODO (3) Within this method, build the URL with the text from the EditText and set the built URL to the TextView
    private void makeGithubSearchQuery() {

        //String searchStr = getSearchStringEditText();

        //mPresenter.makeGithubSearchQuery(searchStr);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }







}
