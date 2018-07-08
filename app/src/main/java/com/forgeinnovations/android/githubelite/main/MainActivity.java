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
import com.forgeinnovations.android.githubelite.tabs.BookmarkTab;
import com.forgeinnovations.android.githubelite.tabs.SearchTab;
import com.forgeinnovations.android.githubelite.tabs.Tab3Fragment;
import com.forgeinnovations.android.githubelite.view.PageAdapter;

public class MainActivity extends AppCompatActivity implements SearchTab.OnFragmentInteractionListener,BookmarkTab.OnFragmentInteractionListener,Tab3Fragment.OnFragmentInteractionListener {


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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Bookmarks"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

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

            mPresenter.makeGithubSearchQuery(query);


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


}
