package com.forgeinnovations.android.githubelite.view;

/**
 * Created by Rahul B Gautam on 7/4/18.
 */

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.bookmark.BookmarkPresenter;
import com.forgeinnovations.android.githubelite.data.GitHubBookmarkItemAdapter;
import com.forgeinnovations.android.githubelite.data.GitHubListItemAdapter;
import com.forgeinnovations.android.githubelite.data.GitHubRestAdapter;
import com.forgeinnovations.android.githubelite.datamodel.GitHubBookmarkResponse;
import com.forgeinnovations.android.githubelite.datamodel.Item;
import com.forgeinnovations.android.githubelite.db.DataManager;
import com.forgeinnovations.android.githubelite.db.GitHubSearchOpenHelper;
import com.forgeinnovations.android.githubelite.main.MainPresenter;
import com.forgeinnovations.android.githubelite.main.MainView;
import com.forgeinnovations.android.githubelite.tabs.SearchTab;
import com.forgeinnovations.android.githubelite.tabs.Tab3Fragment;

import java.util.List;

/**
 * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
 * The individual pages are simple and just display two lines of text. The important section of
 * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
 * {@link SlidingTabLayout}.
 */
public class SamplePagerAdapter extends FragmentStatePagerAdapter implements MainView, LoaderManager.LoaderCallbacks<GitHubBookmarkResponse> {

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
    private int mNoOfTabs;

    private Context mContext;


    public String mKeyword;

    private MenuItem mBookmarkMenuitem;

    public SamplePagerAdapter(FragmentManager fm, int NumberOfTabs, Context context){
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
        this.mContext =context;

    }


    /**
     * @return the number of pages to display
     */
    @Override
    public int getCount() {
        return mNoOfTabs;
    }

    /**
     * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
     * same object as the {@link View} added to the {@link ViewPager}.
     */
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                SearchTab searchTab= new SearchTab();
                return searchTab;
            case 1:
                Tab3Fragment tab3 =  new Tab3Fragment();
                return tab3;
            default:
                return null;
        }


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



//    /**
//     * Instantiate the {@link View} which should be displayed at {@code position}. Here we
//     * inflate a layout from the apps resources and then change the text view to signify the position.
//     */
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//
//        // Inflate a new layout from our resources
//        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_bookmark,
//                container, false);
//
//        ///TODO: do conditional inflation according to position
//        //TODO: find a way to display different contents on different tables
//        if (position == 0) {
//
//            // Inflate a new layout from our resources
//            view = getActivity().getLayoutInflater().inflate(R.layout.fragment_search,
//                    container, false);
//
//            mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_github);
//
//
////                PopupMenu p = new PopupMenu(getContext(), null);
////                Menu menu = p.getMenu();
////                getActivity().getMenuInflater().inflate(R.menu.main, menu);
//
//
//
//
//            // Add the newly created View to the ViewPager
//            container.addView(view);
//            Context context = getActivity().getBaseContext();
//            // Retrieve a TextView from the inflated View, and update it's text
////            TextView title = (TextView) view.findViewById(R.id.item_title);
////            title.setText(String.valueOf(position + 1));
//
//            mDbOpenHelper = new GitHubSearchOpenHelper(context);
//
//            TextView contextualtext = getActivity().findViewById(R.id.tv_topText);
//            contextualtext.setText(R.string.searchTabTopText);
//
//            mUrlDisplayTextView = (TextView) view.findViewById(R.id.tv_url_display);
//            //mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
//            // TODO (13) Get a reference to the error TextView using findViewById
//            mErrorMessageTextView = (TextView) view.findViewById(R.id.tv_error_message_display);
//
//            // TODO (25) Get a reference to the ProgressBar using findViewById
//            mDownloadRequestProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_indicator);
//
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//
//            mRecyclerView.setLayoutManager(linearLayoutManager);
//
//            mGitHubListItemAdapter = new GitHubListItemAdapter(getContext());
//
//            mRecyclerView.setAdapter(mGitHubListItemAdapter);
//
//
//            mPresenter = new MainPresenter(this, new GitHubRestAdapter(), mGitHubListItemAdapter);
//
//        }
//
//        if (position == 2) {
//
//            // Inflate a new layout from our resources
//            view = getActivity().getLayoutInflater().inflate(R.layout.fragment_bookmark,
//                    container, false);
//
//
//            mBookmarkRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_bookmark);
//
//            mBookmarkDbHelper = new GitHubSearchOpenHelper(getContext());
//            mGitHubBookmarkItemAdapter = new GitHubBookmarkItemAdapter(getContext(), mBookmarkDbHelper);
//            mBookmarkRecyclerView.setAdapter(mGitHubBookmarkItemAdapter);
//
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//
//
//            mBookmarkRecyclerView.setLayoutManager(linearLayoutManager);
//
//
//            // Add the newly created View to the ViewPager
//            container.addView(view);
//
//            TextView contextualtext = getActivity().findViewById(R.id.tv_topText);
//            contextualtext.setText("List of favorites added \nFavorites can be deleted by clicking on the red bookmark Icon.");
//
////                android.support.v7.app.ActionBar actionBar = getSupportActionBar();
////
////                actionBar.setDisplayHomeAsUpEnabled(true);
//
//
////                mBookmarkPresenter = new BookmarkPresenter(this, mGitHubBookmarkItemAdapter);
//
////                mBookmarkPresenter.inflateMenuItems(this);
//            LoaderManager loaderManager;
//            loaderManager = getLoaderManager();
//
//            loaderManager.initLoader(LOADER_ID, null, this);
//
//        }
//        Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");
//
//        // Return the View
//        return view;
//    }


    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public android.support.v4.content.Loader<GitHubBookmarkResponse> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<GitHubBookmarkResponse>(mContext) {


            /**
             * Subclasses must implement this to take care of loading their data,
             * as per {@link #startLoading()}.  This is not called by clients directly,
             * but as a result of a call to {@link #startLoading()}.
             */
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public GitHubBookmarkResponse loadInBackground() {

                return DataManager.loadFromDatabase(mBookmarkDbHelper);

            }
        };
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     * <p>
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     * <p>
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context, * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(android.support.v4.content.Loader<GitHubBookmarkResponse> loader, GitHubBookmarkResponse data) {
        mGitHubBookmarkItemAdapter.setGitHubData(data);
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(android.support.v4.content.Loader<GitHubBookmarkResponse> loader) {

    }


    private String CreateShareDate(List<Item> bookmarkData) {
        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append("<html><body>");
        for (Item item : bookmarkData) {
            String formattedString = String.format("%s<br/>%s<br/> Stars Count =%s, Watcher Count =%s,Forks Count =%s <br/>%s<br/>", item.getName(), item.getDescription(), item.getStargazersCount(), item.getWatchersCount(), item.getForksCount(), item.getHtmlUrl());
            stringBuilder.append(formattedString);
            stringBuilder.append("<hr/>");
        }

        stringBuilder.append("</body></html>");
        return stringBuilder.toString();
    }


    /**
     * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
     * {@link View}.
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mDbOpenHelper.close();
        mBookmarkDbHelper.close();
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
