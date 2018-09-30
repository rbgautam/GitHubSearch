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

import android.animation.ArgbEvaluator;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.data.GitHubListItemAdapter;
import com.forgeinnovations.android.githubelite.db.GitHubSearchOpenHelper;
import com.forgeinnovations.android.githubelite.tabs.BookmarkTab;
import com.forgeinnovations.android.githubelite.tabs.IntroFrag;
import com.forgeinnovations.android.githubelite.tabs.SearchTab;
import com.forgeinnovations.android.githubelite.tabs.TopDeveloperTab;
import com.forgeinnovations.android.githubelite.tabs.TopRepositoryTab;
import com.forgeinnovations.android.githubelite.utilities.Utility;
import com.forgeinnovations.android.githubelite.view.SectionsPagerAdapter;
import com.forgeinnovations.android.githubelite.view.TabPageAdapter;

public class MainActivity extends AppCompatActivity implements SearchTab.FragmentBookmarkListener, TopRepositoryTab.FragmentTopRepoListener, BookmarkTab.OnFragmentInteractionListener, IntroFrag.OnFragmentInteractionListener {


    private static final String TAG = "Main Activity";
    private MainPresenter mPresenter;

    private GitHubListItemAdapter mGitHubListItemAdapter;
    private RecyclerView mRecyclerView;
    private TabPageAdapter mAdapter;
    private GitHubSearchOpenHelper mDbOpenHelper;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView[] indicators;
    public ImageButton mNextBtn;
    public Button mSkipBtn, mFinishBtn;
    public ImageView zero, one, two;
    private SectionsPagerAdapter mIntroAdapter;
    private LinearLayout mIntroAppLayout;
    private LinearLayout mIntroFragment;
    public int page = 0;   //  to track page position

    private View.OnClickListener searchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //makeGithubSearchQuery();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mDbOpenHelper = new GitHubSearchOpenHelper(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mIntroAppLayout = (LinearLayout) findViewById(R.id.app_intro);

        setSupportActionBar(mToolbar);
        mToolbar.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE); //Hide when displaying Onboarding



        if (Utility.ShowOnBoarding(MainActivity.this )) {
            Log.i(TAG,"onBoarding on");
            showOnBoardingScreen();
        } else {
            Log.i(TAG,"onBoarding off");
            showMainScreen();
        }


        handleIntent(getIntent());
    }

    private void hideBoardingScreen() {

        //getFragmentManager().beginTransaction().replace(R.id.hosted_fragment, fragment).commit();

    }
    private void showOnBoardingScreen() {

        mIntroAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        final IntroFrag introTab =  IntroFrag.newInstance(1);

        mViewPager.setAdapter(mIntroAdapter);
        mViewPager.setCurrentItem(0);

        mNextBtn = (ImageButton) findViewById(R.id.intro_btn_next);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
            mNextBtn.setImageDrawable(
                    Utility.tintMyDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_chevron_right_24dp), Color.WHITE)
            );

        mSkipBtn = (Button) findViewById(R.id.intro_btn_skip);
        mFinishBtn = (Button) findViewById(R.id.intro_btn_finish);


        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                page += 1;
                Log.i(TAG, "next clicked " + String.valueOf(page) );

                updateIndicators(page);
                mViewPager.setCurrentItem(page, true);

            }
        });

        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMainScreen();
            }
        });

        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  update 1st time pref
                Utility.SkipOnBoarding(MainActivity.this);

            }
        });

        zero = (ImageView) findViewById(R.id.intro_indicator_0);
        one = (ImageView) findViewById(R.id.intro_indicator_1);
        two = (ImageView) findViewById(R.id.intro_indicator_2);
        indicators = new ImageView[]{zero, one, two};

        final int color1 = ContextCompat.getColor(this, R.color.cyan);
        final int color2 = ContextCompat.getColor(this, R.color.orange);
        final int color3 = ContextCompat.getColor(this, R.color.green);

        final int[] colorList = new int[]{color1, color2, color3};
        indicators = new ImageView[]{zero, one, two};
        final ArgbEvaluator evaluator = new ArgbEvaluator();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(TAG, "On page scrolled " + String.valueOf(position));
             /*
             color update
              */
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 2 ? position : position + 1]);
                mViewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "On page selected");
                page = position;
                updateIndicators(page);

                switch (position) {
                    case 0:
                        mViewPager.setBackgroundColor(color1);
                        break;
                    case 1:
                        mViewPager.setBackgroundColor(color2);
                        break;
                    case 2:
                        mViewPager.setBackgroundColor(color3);
                        break;
                }

                mNextBtn.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                mFinishBtn.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }
    private void showMainScreen() {

        mViewPager.clearOnPageChangeListeners();
        mViewPager.removeView(mIntroAppLayout);
        mViewPager = null;
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(null);
        mViewPager.setBackgroundColor(getResources().getColor(android.R.color.white));
        mAdapter = new TabPageAdapter(getSupportFragmentManager());
        //Log.i(TAG,"onBoarding off");
        mIntroAppLayout.setVisibility(View.GONE);
        mToolbar.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.VISIBLE); //Hide when displaying Onboarding
        mTabLayout.addTab(mTabLayout.newTab().setText("Top\nRepositories"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Top\nDevelopers"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Search"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Bookmarks"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        mViewPager.setOffscreenPageLimit(3);

        //mAdapter = new TabPageAdapter(getSupportFragmentManager());
        //Adding TopDev tab
        TopRepositoryTab topRepo = new TopRepositoryTab();

        //if(findViewById(R.id.intro_tab_layout) != null)
        //    getSupportFragmentManager().beginTransaction().replace(R.id.intro_tab_layout, topRepo).commit();

        mAdapter.addFrag(topRepo, "Top Repositories");
        //Adding TopDev tab
        TopDeveloperTab topDeveloperTab = new TopDeveloperTab();
        //getSupportFragmentManager().beginTransaction().replace(R.id.intro_tab_layout, topDeveloperTab).commit();
        mAdapter.addFrag(topDeveloperTab, "Top Developers");
        //Adding Search Tab
        SearchTab searchTab = new SearchTab();
        mAdapter.addFrag(searchTab, "Search");
        //Adding BookMark tab
        BookmarkTab bookmarkTab = new BookmarkTab();
        mAdapter.addFrag(bookmarkTab, "Bookmarks");

        mViewPager.setAdapter(mAdapter);
        searchTab.setAddBookmarkListener(this);
        topRepo.setAddTopRepoListener(this);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

//                if(findViewById(R.id.intro_tab_layout) != null)
//                    getSupportFragmentManager().beginTransaction().replace(R.id.intro_tab_layout, mAdapter.getItem(tab.getPosition())).commit();

                Log.i("Tab Logger", tab.getText() + " Tab clicked");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


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

            mPresenter.makeGithubSearchQuery(query, mDbOpenHelper);


        }

    }

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
