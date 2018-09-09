package com.forgeinnovations.android.githubelite.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.bookmark.BookmarkPresenter;
import com.forgeinnovations.android.githubelite.data.GitHubTopRepoItemAdapter;
import com.forgeinnovations.android.githubelite.datamodel.GitHubSearch.Item;
import com.forgeinnovations.android.githubelite.datamodel.GitHubTopRepo.GitHubTopRepoResponse;
import com.forgeinnovations.android.githubelite.viewmodel.TopRepoViewModel;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopRepositoryTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopRepositoryTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopRepositoryTab extends Fragment {
    private GitHubTopRepoItemAdapter mGitHubTopDevItemAdapter;
    private RecyclerView mTopRepoRecyclerView;

    private ProgressBar mTopRepoTabProgressbar;
    private TextView mTopRepoTabErrorMessage;
    private ImageView mTopRepoTabErrorImage;
    private ShareActionProvider mShareActionProvider;
    private BookmarkPresenter mBookmarkPresenter;
    private String mGithubShareData;
    private TopDeveloperTab.OnFragmentInteractionListener mListener;

    public TopRepositoryTab() {
    }

    public static TopRepositoryTab newInstance(String param1, String param2) {
        TopRepositoryTab fragment = new TopRepositoryTab();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_repo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTopRepoRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview_toprepo);
        mTopRepoTabProgressbar = (ProgressBar) getActivity().findViewById(R.id.pb_toprepo_loading_indicator);
        mTopRepoTabErrorMessage = (TextView) getActivity().findViewById(R.id.tv_toprepo_error_message);
        mTopRepoTabErrorImage = (ImageView) getActivity().findViewById(R.id.img_error_message);
        mTopRepoTabProgressbar.setVisibility(View.VISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mTopRepoRecyclerView.setLayoutManager(linearLayoutManager);

        mGitHubTopDevItemAdapter = new GitHubTopRepoItemAdapter(getActivity());

        mTopRepoRecyclerView.setAdapter(mGitHubTopDevItemAdapter);
        LoadGitHubTopRepos();
    }

    private void LoadGitHubTopRepos() {

        TopRepoViewModel model = ViewModelProviders.of(this).get(TopRepoViewModel.class);

        Observer<GitHubTopRepoResponse> observer = new Observer<GitHubTopRepoResponse>() {
            @Override
            public void onChanged(@Nullable GitHubTopRepoResponse topRepoList) {
                if(topRepoList.getErrorMessage() == null){
                    mGitHubTopDevItemAdapter = new GitHubTopRepoItemAdapter(getActivity());
                    mGitHubTopDevItemAdapter.setGitHubRepoDevData(topRepoList);
                    mTopRepoTabProgressbar.setVisibility(View.INVISIBLE);
                    mTopRepoTabErrorMessage.setVisibility(View.INVISIBLE);
                    mTopRepoTabErrorImage.setVisibility(View.INVISIBLE);
                    mTopRepoRecyclerView.setAdapter(mGitHubTopDevItemAdapter);
                }else {
                    mTopRepoTabProgressbar.setVisibility(View.INVISIBLE);
                    mTopRepoTabErrorMessage.setVisibility(View.VISIBLE);
                    mTopRepoTabErrorImage.setVisibility(View.VISIBLE);
                    mTopRepoTabErrorMessage.setText("Oops something went wrong,\nTry Again after sometime");
                }
            }
        };
        //TODO: Pass langauage and duration from UI
        model.GetTopRepos ("java", "weekly").observe(this, observer);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.bookmark_menu, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        if (mShareActionProvider != null)
            mShareActionProvider.setShareIntent(createShareDataIntent());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mBookmarkPresenter.onMenuItemClicked(item);
        return super.onOptionsItemSelected(item);
    }

    private String CreateShareData(HashMap<Integer, Item> bookmarkData) {
        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append("<html><body>");
        for (Item item : bookmarkData.values()) {
            String formattedString = String.format("%s<br/>%s<br/> Stars Count =%s, Watcher Count =%s,Forks Count =%s <br/>%s<br/>", item.getName(), item.getDescription(), item.getStargazersCount(), item.getWatchersCount(), item.getForksCount(), item.getHtmlUrl());
            stringBuilder.append(formattedString);
            stringBuilder.append("<hr/>");
        }

        stringBuilder.append("</body></html>");
        return stringBuilder.toString();
    }

    //final HashMap<Integer,Item> bookmarkData = data.getBookmarkItems();
    //Async
    //mGithubShareData = CreateShareData(bookmarkData);
    //post execute
    //if(mShareActionProvider != null)
    //                    mShareActionProvider.setShareIntent(createShareDataIntent());

    private Intent createShareDataIntent() {
        String shareData = mGithubShareData;
        if (mGithubShareData == null) {
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


    public void refreshRecyclerView() {
        Log.i("bookmark trace", "refreshRecyclerView ");
        //mLoaderManager.restartLoader(LOADER_ID,null,this);
        //mGitHubTopDevItemAdapter.notifyItemChanged(0);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
