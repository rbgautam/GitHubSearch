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
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.data.GitHubListItemAdapter;
import com.forgeinnovations.android.githubelite.db.GitHubSearchOpenHelper;
import com.forgeinnovations.android.githubelite.tabs.BookmarkTab;
import com.forgeinnovations.android.githubelite.tabs.SearchTab;
import com.forgeinnovations.android.githubelite.tabs.TopDeveloperTab;
import com.forgeinnovations.android.githubelite.tabs.TopRepositoryTab;
import com.forgeinnovations.android.githubelite.view.TabPageAdapter;

public class MainActivity extends AppCompatActivity implements SearchTab.FragmentBookmarkListener,TopRepositoryTab.FragmentTopRepoListener ,BookmarkTab.OnFragmentInteractionListener {


    private MainPresenter mPresenter;

    private GitHubListItemAdapter mGitHubListItemAdapter;
    private RecyclerView mRecyclerView;
    private TabPageAdapter mAdapter;
    private GitHubSearchOpenHelper mDbOpenHelper;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDbOpenHelper = new GitHubSearchOpenHelper(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Top\nRepositories"));
        tabLayout.addTab(tabLayout.newTab().setText("Top\nDevelopers"));
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Bookmarks"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);

        mAdapter = new TabPageAdapter(getSupportFragmentManager());
        //Adding TopDev tab
        TopRepositoryTab topRepo = new TopRepositoryTab();
        mAdapter.addFrag(topRepo, "Top Repositories");
        //Adding TopDev tab
        TopDeveloperTab topDeveloperTab = new TopDeveloperTab();
        mAdapter.addFrag(topDeveloperTab, "Top Developers");
        //Adding Search Tab
        SearchTab searchTab = new SearchTab();
        mAdapter.addFrag(searchTab, "Search");
        //Adding BookMark tab
        BookmarkTab bookmarkTab = new BookmarkTab();
        mAdapter.addFrag(bookmarkTab, "Bookmarks");


        viewPager.setAdapter(mAdapter);
        searchTab.setAddBookmarkListener(this);
        topRepo.setAddTopRepoListener(this);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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

            mPresenter.makeGithubSearchQuery(query,mDbOpenHelper);


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


    @Override
    public void onFragmentInteraction(Uri uri) {
        String temp = uri.toString();
    }


    @Override
    public void onFragmentAddBookMark(String tab) {

        BookmarkTab fragment = (BookmarkTab) mAdapter.getItem(3);
        fragment.refreshRecyclerView();
    }

    @Override
    public void onFragmentAddTopRepoBookMark(String tab) {
        BookmarkTab fragment = (BookmarkTab) mAdapter.getItem(3);
        fragment.refreshRecyclerView();
    }
}
