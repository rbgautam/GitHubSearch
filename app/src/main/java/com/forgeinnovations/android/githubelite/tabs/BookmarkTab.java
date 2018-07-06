package com.forgeinnovations.android.githubelite.tabs;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.bookmark.BookmarkPresenter;
import com.forgeinnovations.android.githubelite.data.GitHubBookmarkItemAdapter;
import com.forgeinnovations.android.githubelite.datamodel.GitHubSeachResponse;
import com.forgeinnovations.android.githubelite.db.GitHubSearchOpenHelper;

/**
 * Created by Rahul B Gautam on 7/4/18.
 */
public class BookmarkTab extends Fragment implements LoaderManager.LoaderCallbacks<GitHubSeachResponse>{


    public static final int LOADER_ID = 890; // random number
    private BookmarkPresenter mBookmarkPresenter;
    private GitHubBookmarkItemAdapter mGitHubBookmarkItemAdapter;
    private GitHubSearchOpenHelper mBookmarkDbHelper;
    private RecyclerView mBookmarkRecyclerView;
    private ShareActionProvider mShareActionProvider;
    private String mGithubShareData;

    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public Loader<GitHubSeachResponse> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<GitHubSeachResponse> loader, GitHubSeachResponse data) {

    }

    @Override
    public void onLoaderReset(Loader<GitHubSeachResponse> loader) {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.fragment_bookmark);
        mBookmarkDbHelper =  new GitHubSearchOpenHelper(getActivity());
        mGitHubBookmarkItemAdapter = new GitHubBookmarkItemAdapter(getActivity(), mBookmarkDbHelper);
        mBookmarkRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview_bookmark);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);



        mBookmarkRecyclerView.setLayoutManager(linearLayoutManager);

        mBookmarkRecyclerView.setAdapter(mGitHubBookmarkItemAdapter);


//        android.support.v7.app.ActionBar actionBar = getActivity().getSupportActionBar();
//
//        actionBar.setDisplayHomeAsUpEnabled(true);



//        mBookmarkPresenter = new BookmarkPresenter(getActivity(), mGitHubBookmarkItemAdapter);
//
//        mBookmarkPresenter.inflateMenuItems(this);
        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(LOADER_ID,null,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }
}
