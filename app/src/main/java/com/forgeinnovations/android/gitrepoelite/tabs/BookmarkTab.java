package com.forgeinnovations.android.gitrepoelite.tabs;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.forgeinnovations.android.gitrepoelite.R;
import com.forgeinnovations.android.gitrepoelite.bookmark.BookmarkPresenter;
import com.forgeinnovations.android.gitrepoelite.data.GitHubBookmarkItemAdapter;
import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.GitHubBookmarkResponse;
import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.Item;
import com.forgeinnovations.android.gitrepoelite.db.DataManager;
import com.forgeinnovations.android.gitrepoelite.db.GitHubSearchOpenHelper;
import com.forgeinnovations.android.gitrepoelite.utilities.BookmarkLinearLayoutManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Rahul B Gautam on 7/4/18.
 */
public class BookmarkTab extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<GitHubBookmarkResponse>{


    public static final int LOADER_ID = 891; // random number
    private BookmarkPresenter mBookmarkPresenter;
    private GitHubBookmarkItemAdapter mGitHubBookmarkItemAdapter;
    private GitHubSearchOpenHelper mBookmarkDbHelper;
    private RecyclerView mBookmarkRecyclerView;
    private ShareActionProvider mShareActionProvider;
    private String mGithubShareData;

    private OnFragmentInteractionListener mListener;

    private static String TAG = "Bookmark Fragment";
    public LoaderManager getBookLoaderManager() {
        return mLoaderManager;
    }

    private LoaderManager mLoaderManager;

    public BookmarkTab(){}

    public static BookmarkTab newInstance(String param1, String param2) {
        BookmarkTab fragment = new BookmarkTab();

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.i(TAG,"ON Attach");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBookmarkDbHelper =  new GitHubSearchOpenHelper(getActivity());
        mGitHubBookmarkItemAdapter = new GitHubBookmarkItemAdapter(getActivity(), mBookmarkDbHelper);
        mBookmarkRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview_bookmark);
        BookmarkLinearLayoutManager linearLayoutManager =  new BookmarkLinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mBookmarkRecyclerView.setLayoutManager(linearLayoutManager);

        mBookmarkRecyclerView.setAdapter(mGitHubBookmarkItemAdapter);

        mLoaderManager = getLoaderManager();

        mLoaderManager.initLoader(LOADER_ID,null,this);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mBookmarkDbHelper != null)
            mBookmarkDbHelper.close();
    }

    @Override
    public Loader<GitHubBookmarkResponse> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<GitHubBookmarkResponse>(getActivity()) {


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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.bookmark_menu, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        if(mShareActionProvider != null)
            mShareActionProvider.setShareIntent(createShareDataIntent());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mBookmarkPresenter.onMenuItemClicked(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadFinished(Loader<GitHubBookmarkResponse> loader, GitHubBookmarkResponse data) {
        int bookmarkCount = data.getBookmarkCount();
        Log.i("bookmark trace",String.format("loader finished book mark count = %d",bookmarkCount));
       if(bookmarkCount == 0)
            return;
        mBookmarkRecyclerView.getRecycledViewPool().clear();
        mGitHubBookmarkItemAdapter.setGitHubData(data);

        final HashMap<String,Item> bookmarkData = data.getBookmarkItems();
        AsyncTask<Void,Void,Void> asyncDataCreation =  new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                mGithubShareData = CreateShareData(bookmarkData);
                return null;
            }

            /**
             * <p>Runs on the UI thread after {@link #doInBackground}. The
             * specified result is the value returned by {@link #doInBackground}.</p>
             * <p>
             * <p>This method won't be invoked if the task was cancelled.</p>
             *
             * @param aVoid The result of the operation computed by {@link #doInBackground}.
             * @see #onPreExecute
             * @see #doInBackground
             * @see #onCancelled(Object)
             */
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(mShareActionProvider != null)
                    mShareActionProvider.setShareIntent(createShareDataIntent());
            }
        };

        asyncDataCreation.execute();
    }

    @Override
    public void onLoaderReset(Loader<GitHubBookmarkResponse> loader) {

    }



    private String CreateShareData(HashMap<String,Item> bookmarkData) {
        StringBuilder stringBuilder = new StringBuilder();

    try {
            stringBuilder.append("<html><body>");
            Iterator it = bookmarkData.entrySet().iterator();

            while (it.hasNext())
            {
                Map.Entry currItem = (Map.Entry) it.next();
                Item item = (Item)currItem.getValue();
                String formattedString = String.format("%s<br/>%s<br/> Stars Count =%s, Watcher Count =%s,Forks Count =%s <br/>%s<br/>",item.getName(),item.getDescription(),item.getStargazersCount(), item.getWatchersCount(),item.getForksCount(), item.getHtmlUrl());
                stringBuilder.append(formattedString);
                stringBuilder.append("<hr/>");
            }

            stringBuilder.append("</body></html>");
        }

        catch(Exception ex){

        }

        return stringBuilder.toString();
    }

    private Intent createShareDataIntent() {
        String shareData = mGithubShareData;
        if(mGithubShareData == null){
            return null;

        }
        Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/html")
                .setHtmlText(mGithubShareData)
                .getIntent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        return shareIntent;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }

    public void refreshRecyclerView(){
        Log.i("bookmark trace","refreshRecyclerView " );
        mLoaderManager.restartLoader(LOADER_ID,null,this);
        mGitHubBookmarkItemAdapter.notifyDataSetChanged();
    }
}
