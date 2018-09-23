package com.forgeinnovations.android.githubelite.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.bookmark.BookmarkPresenter;
import com.forgeinnovations.android.githubelite.data.GitHubBookmarkItemAdapter;
import com.forgeinnovations.android.githubelite.data.GitHubListItemAdapter;
import com.forgeinnovations.android.githubelite.data.GitHubRestAdapter;
import com.forgeinnovations.android.githubelite.db.GitHubSearchOpenHelper;
import com.forgeinnovations.android.githubelite.view.PageAdapter;
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

    //members for bookmark tab
    public static final int LOADER_ID = 890; // random number
    private BookmarkPresenter mBookmarkPresenter;
    private GitHubBookmarkItemAdapter mGitHubBookmarkItemAdapter;
    private GitHubSearchOpenHelper mBookmarkDbHelper;
    private RecyclerView mBookmarkRecyclerView;
    private ShareActionProvider mShareActionProvider;
    private String mGithubShareData;
    private Menu mMenu;


    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        mDbOpenHelper.close();
        mBookmarkDbHelper.close();
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
//        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
//
//        mViewPager.setAdapter(new SamplePagerAdapter(getFragmentManager(),2, getActivity()));
//        // END_INCLUDE (setup_viewpager)
//
//        // BEGIN_INCLUDE (setup_slidingtablayout)
//        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
//        // it's PagerAdapter set.
//        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
//        mSlidingTabLayout.setViewPager(mViewPager);

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
        //mMenu = menu;

        //Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_widget).getActionView();


        searchView.setOnQueryTextListener(createSearchQueryListener());

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        super.onCreateOptionsMenu(menu, inflater);
    }


//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            getMenuInflater().inflate(R.menu.bookmark_menu, menu);
//
//            // Locate MenuItem with ShareActionProvider
//            MenuItem item = menu.findItem(R.id.menu_item_share);
//
//            // Fetch and store ShareActionProvider
//            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
//            //mGithubShareData = "test data";
//
//            return true;
//        }


//    private Intent createShareDataIntent() {
//        Intent shareIntent = ShareCompat.IntentBuilder.from(get)
//                .setType("text/html")
//                .setHtmlText(mGithubShareData)
//                .getIntent();
//        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
//        return shareIntent;
//    }


    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
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
                mPresenter.makeGithubSearchQuery(query,mDbOpenHelper);
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String query) {

                return false;
            }

        };
    }
    // END_INCLUDE (fragment_onviewcreated)


}
