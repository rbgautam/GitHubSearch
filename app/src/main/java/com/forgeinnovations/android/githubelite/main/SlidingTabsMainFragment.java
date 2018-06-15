package com.forgeinnovations.android.githubelite.main;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.data.GitHubListItemAdapter;
import com.forgeinnovations.android.githubelite.data.GitHubRestAdapter;
import com.forgeinnovations.android.githubelite.db.GitHubSearchOpenHelper;
import com.forgeinnovations.android.githubelite.view.SlidingTabLayout;
import com.github.amlcurran.showcaseview.ShowcaseView;

/**
 * Created by Rahul B Gautam on 6/9/18.
 */
public class SlidingTabsMainFragment extends Fragment {

    static final String LOG_TAG = "SlidingTabsMainFragment";

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    private TextView mSearchResultsTextView;

    // TODO (12) Create a variable to store a reference to the error message TextView
    private TextView mErrorMessageTextView;


    // TODO (24) Create a ProgressBar variable to store a reference to the ProgressBar
    private ProgressBar mDownloadRequestProgressBar;

    GitHubRestAdapter queryAdapter = new GitHubRestAdapter();

    private MainPresenter mPresenter;

    private GitHubListItemAdapter mGitHubListItemAdapter;
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;

    private GitHubSearchOpenHelper mDbOpenHelper;
    private ImageButton mSearchButton;

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }

    public String mKeyword;
    private ShowcaseView showcaseView;
    private int counter = 0;
    private MenuItem mBookmarkMenuitem;
    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;

    /**
     * Inflates the {@link View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

    // BEGIN_INCLUDE (fragment_onviewcreated)

    /**
     * This is called after the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has finished.
     * Here we can pick out the {@link View}s we need to configure from the content view.
     * <p>
     * We set the {@link ViewPager}'s adapter to be an instance of {@link SamplePagerAdapter}. The
     * {@link SlidingTabLayout} is then given the {@link ViewPager} so that it can populate itself.
     *
     * @param view View created in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);

        mGitHubListItemAdapter = new GitHubListItemAdapter(getContext());


        // END_INCLUDE (setup_slidingtablayout)
    }

    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(Activity)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * <p>
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>Any restored child fragments will be created before the base
     * <code>Fragment.onCreate</code> method returns.</p>
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    /**
     * Initialize the contents of the Fragment host's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.  See
     * {@link Activity#onCreateOptionsMenu(Menu) Activity.onCreateOptionsMenu}
     * for more information.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);


        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_widget).getActionView();


        searchView.setOnQueryTextListener(createSearchQueryListener());

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        mPresenter.onMenuItemClicked(item);

        mPresenter.hideKeyboard(getActivity());
        return super.onOptionsItemSelected(item);
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
                //setSearchStringEditText(query);
                mPresenter.makeGithubSearchQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String query) {

                return false;
            }

        };
    }
    // END_INCLUDE (fragment_onviewcreated)

    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class SamplePagerAdapter extends PagerAdapter implements MainView {

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 4;
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)

        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {

            String result = "";
            switch (position) {
                case 0:
                    result = "Search";
                    break;
                case 1:
                    result = "Trends";
                    break;
                case 2:
                    result = "Favorites";
                    break;
                case 3:
                    result = "News";
                    break;
                default:
                    break;

            }

            return result;
        }
        // END_INCLUDE (pageradapter_getpagetitle)

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // Inflate a new layout from our resources
            View view = getActivity().getLayoutInflater().inflate(R.layout.pager_bookmark,
                    container, false);

            ///TODO: do conditional inflation according to position
            //TODO: find a way to display different contents on different tables
            if (position == 0) {

                // Inflate a new layout from our resources
                view = getActivity().getLayoutInflater().inflate(R.layout.pager_item,
                        container, false);

                mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_github);
                mRecyclerView.setAdapter(mGitHubListItemAdapter);

                // Add the newly created View to the ViewPager
                container.addView(view);

                Context context = getActivity().getBaseContext();
                // Retrieve a TextView from the inflated View, and update it's text
//            TextView title = (TextView) view.findViewById(R.id.item_title);
//            title.setText(String.valueOf(position + 1));

                mDbOpenHelper = new GitHubSearchOpenHelper(context);


                mUrlDisplayTextView = (TextView) view.findViewById(R.id.tv_url_display);
                //mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
                // TODO (13) Get a reference to the error TextView using findViewById
                mErrorMessageTextView = (TextView) view.findViewById(R.id.tv_error_message_display);

                // TODO (25) Get a reference to the ProgressBar using findViewById
                mDownloadRequestProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_indicator);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

                mRecyclerView.setLayoutManager(linearLayoutManager);

                mGitHubListItemAdapter = new GitHubListItemAdapter(getContext());

                mRecyclerView.setAdapter(mGitHubListItemAdapter);


                mPresenter = new MainPresenter(this, new GitHubRestAdapter(), mGitHubListItemAdapter);

            }
            Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");

            // Return the View
            return view;
        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mDbOpenHelper.close();
            Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
        }


//        @Override
//        protected void onDestroy() {
//            //mDbOpenHelper.getWritableDatabase();
//            mDbOpenHelper.close();
//            super.onDestroy();
//        }

        @Override
        public void showResultsTextView() {
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void hideResultsTextView() {
            mRecyclerView.setVisibility(View.INVISIBLE);
            //Toast.makeText(this, R.string.error_message, Toast.LENGTH_LONG);
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
    }
}
