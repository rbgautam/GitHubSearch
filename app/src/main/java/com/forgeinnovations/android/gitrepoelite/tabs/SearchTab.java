package com.forgeinnovations.android.gitrepoelite.tabs;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.forgeinnovations.android.gitrepoelite.R;
import com.forgeinnovations.android.gitrepoelite.bookmark.BookmarkPresenter;
import com.forgeinnovations.android.gitrepoelite.data.GitHubBookmarkItemAdapter;
import com.forgeinnovations.android.gitrepoelite.data.GitHubListItemAdapter;
import com.forgeinnovations.android.gitrepoelite.data.GitHubRestAdapter;
import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.GitHubBookmarkResponse;
import com.forgeinnovations.android.gitrepoelite.db.DataManager;
import com.forgeinnovations.android.gitrepoelite.db.GitHubSearchOpenHelper;
import com.forgeinnovations.android.gitrepoelite.main.MainPresenter;
import com.forgeinnovations.android.gitrepoelite.main.MainView;

/**
 * Created by Rahul B Gautam on 7/4/18.
 */
public class SearchTab extends Fragment implements MainView, LoaderManager.LoaderCallbacks<GitHubBookmarkResponse>, GitHubListItemAdapter.AddBookmarkListener {

    //members for bookmark tab
    public static final int LOADER_ID = 890; // random number
    static final String LOG_TAG = "SlidingTabsMainFragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    //private OnFragmentInteractionListener mListener;
    GitHubRestAdapter queryAdapter = new GitHubRestAdapter();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText mSearchBoxEditText;
    private TextView mUrlDisplayTextView;
    private TextView mSearchResultsTextView;
    // TODO (12) Create a variable to store a reference to the error message TextView
    private TextView mErrorMessageTextView;
    // TODO (24) Create a ProgressBar variable to store a reference to the ProgressBar
    private ProgressBar mDownloadRequestProgressBar;
    private MainPresenter mPresenter;
    private GitHubListItemAdapter mGitHubListItemAdapter;
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private GitHubSearchOpenHelper mDbOpenHelper;
    private BookmarkPresenter mBookmarkPresenter;
    private GitHubBookmarkItemAdapter mGitHubBookmarkItemAdapter;
    private GitHubSearchOpenHelper mBookmarkDbHelper;
    private RecyclerView mBookmarkRecyclerView;
    private ShareActionProvider mShareActionProvider;
    private String mGithubShareData;
    private Menu mMenu;
    private String mKeyword;
    private LoaderManager mLoaderManager;
    private FragmentBookmarkListener onFragmentAddBookmarkListener;

    public SearchTab() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchTab newInstance(String param1, String param2) {
        SearchTab fragment = new SearchTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        //Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_widget).getActionView();

        searchView.setOnQueryTextListener(createSearchQueryListener(menu));

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        Log.v("Search Tab", "Menu inflate");
        searchView.setIconified(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_github);

        // Add the newly created View to the ViewPager

        Context context = getActivity().getBaseContext();

        mDbOpenHelper = new GitHubSearchOpenHelper(context);
        mBookmarkDbHelper = new GitHubSearchOpenHelper(getActivity());

        mGitHubBookmarkItemAdapter = new GitHubBookmarkItemAdapter(getActivity(), mBookmarkDbHelper);

//        TextView contextualtext = getActivity().findViewById(R.id.tv_contextual_text);
//        contextualtext.setText(R.string.searchTabTopText);

        mUrlDisplayTextView = (TextView) view.findViewById(R.id.tv_url_display);
        //mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
        // TODO (13) Get a reference to the error TextView using findViewById
        mErrorMessageTextView = (TextView) view.findViewById(R.id.tv_error_message_display);

        // TODO (25) Get a reference to the ProgressBar using findViewById
        mDownloadRequestProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading_indicator);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        //mGitHubBookmarkItemAdapter.setGitHubTopDevData(data);

        mGitHubListItemAdapter = new GitHubListItemAdapter(getActivity());

        mRecyclerView.setAdapter(mGitHubListItemAdapter);

        mRecyclerView.setLayoutManager(linearLayoutManager);

        mPresenter = new MainPresenter(this, new GitHubRestAdapter(), mGitHubListItemAdapter);

        //mLoaderManager = getLoaderManager();
        //mLoaderManager.initLoader(LOADER_ID,null,this);

        mGitHubListItemAdapter.setAddBookmarkListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        if (mDbOpenHelper != null)
            mDbOpenHelper.close();
        if (mBookmarkDbHelper != null)
            mBookmarkDbHelper.close();
        mPresenter.hideKeyboard(getActivity());
        super.onDetach();
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        mPresenter.hideKeyboard(getActivity());
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {

        if (mDbOpenHelper != null)
            mDbOpenHelper.close();
        if (mBookmarkDbHelper != null)
            mBookmarkDbHelper.close();
        super.onDestroy();
    }

    /**
     * Creates and returns a listener, which allows to start a search query when the user enters
     * text.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * OnQueryTextListener}
     */
    public SearchView.OnQueryTextListener createSearchQueryListener(final Menu menu) {
        return new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                //setSearchStringEditText(query);
                SearchView searchView = (SearchView) menu.findItem(R.id.action_search_widget).getActionView();

                searchView.setIconified(true);
                searchView.setIconified(true);
                mPresenter.makeGithubSearchQuery(query, mDbOpenHelper);
                mPresenter.hideKeyboard(getActivity());

                return true;
            }

            @Override
            public boolean onQueryTextChange(final String query) {

                return false;
            }

        };
    }

    @Override
    public void showResultsTextView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideResultsTextView() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_LONG);
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

    @Override
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

    @Override
    public Loader<GitHubBookmarkResponse> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<GitHubBookmarkResponse>(getContext()) {


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

    @Override
    public void onLoadFinished(Loader<GitHubBookmarkResponse> loader, GitHubBookmarkResponse data) {
        if (data != null) {
            mGitHubBookmarkItemAdapter.setGitHubData(data);

        }

    }

    @Override
    public void onLoaderReset(Loader<GitHubBookmarkResponse> loader) {

    }

    @Override
    public void onAddBookmark() {
        //mLoaderManager.restartLoader(LOADER_ID,null,this);
        Log.i("bookmark trace", "inside onAddbookmark");
        onFragmentAddBookmarkListener.onFragmentAddBookMark("Search");
    }

    public FragmentBookmarkListener getAddBookmarkListener() {
        return this.onFragmentAddBookmarkListener;
    }

    public void setAddBookmarkListener(FragmentBookmarkListener fragmentAddBookmarkListener) {
        this.onFragmentAddBookmarkListener = fragmentAddBookmarkListener;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface FragmentBookmarkListener {
        // TODO: Update argument type and name
        void onFragmentAddBookMark(String tab);
    }

}
