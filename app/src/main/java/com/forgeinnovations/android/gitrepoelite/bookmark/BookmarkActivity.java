package com.forgeinnovations.android.gitrepoelite.bookmark;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;

import com.forgeinnovations.android.gitrepoelite.R;
import com.forgeinnovations.android.gitrepoelite.data.GitHubBookmarkItemAdapter;
import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.GitHubBookmarkResponse;
import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.Item;
import com.forgeinnovations.android.gitrepoelite.db.DataManager;
import com.forgeinnovations.android.gitrepoelite.db.GitHubSearchOpenHelper;

import java.util.HashMap;

public class BookmarkActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<GitHubBookmarkResponse> {

    public static final int LOADER_ID = 890; // random number
    private BookmarkPresenter mBookmarkPresenter;
    private GitHubBookmarkItemAdapter mGitHubBookmarkItemAdapter;
    private GitHubSearchOpenHelper mBookmarkDbHelper;
    private RecyclerView mBookmarkRecyclerView;
    private ShareActionProvider mShareActionProvider;
    private String mGithubShareData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bookmark);
//        mBookmarkDbHelper =  new GitHubSearchOpenHelper(this);
//        mGitHubBookmarkItemAdapter = new GitHubBookmarkItemAdapter(this, mBookmarkDbHelper);
//        mBookmarkRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_bookmark);
//        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//
//
//
//        mBookmarkRecyclerView.setLayoutManager(linearLayoutManager);
//
//        mBookmarkRecyclerView.setAdapter(mGitHubBookmarkItemAdapter);
//
//
//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//
//        actionBar.setDisplayHomeAsUpEnabled(true);



//        mBookmarkPresenter = new BookmarkPresenter(this, mGitHubBookmarkItemAdapter);
//
//        mBookmarkPresenter.inflateMenuItems(this);
        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(LOADER_ID,null,this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBookmarkDbHelper.close();
    }


    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<GitHubBookmarkResponse> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<GitHubBookmarkResponse>(this) {


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
     * the  constructor <em>without</em> passing
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
    public void onLoadFinished(Loader<GitHubBookmarkResponse> loader, GitHubBookmarkResponse data) {
        //mGitHubBookmarkItemAdapter.setGitHubData(data);

        if(data.getBookmarkItems().size() == 0)
            return;

        final HashMap<String,Item> bookmarkData = data.getBookmarkItems();
        AsyncTask<Void,Void,Void> asyncDataCreation =  new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mGithubShareData = CreateShareDate(bookmarkData);
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
                mShareActionProvider.setShareIntent(createShareDataIntent());
            }
        };

        asyncDataCreation.execute();


    }

    private String CreateShareDate(HashMap<String,Item> bookmarkData) {
        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append("<html><body>");
        for(Item item: bookmarkData.values()){
            String formattedString = String.format("%s<br/>%s<br/> Stars Count =%s, Watcher Count =%s,Forks Count =%s <br/>%s<br/>",item.getName(),item.getDescription(),item.getStargazersCount(), item.getWatchersCount(),item.getForksCount(), item.getHtmlUrl());
            stringBuilder.append(formattedString);
            stringBuilder.append("<hr/>");
        }

        stringBuilder.append("</body></html>");
        return stringBuilder.toString();
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<GitHubBookmarkResponse> loader) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bookmark_menu, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        //mGithubShareData = "test data";

        return true;
    }


    private Intent createShareDataIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/html")
                .setHtmlText(mGithubShareData)
                .getIntent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        return shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mBookmarkPresenter.onMenuItemClicked(item);


        return super.onOptionsItemSelected(item);
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
