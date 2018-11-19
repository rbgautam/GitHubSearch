package com.forgeinnovations.android.gitrepoelite.tabs;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.forgeinnovations.android.gitrepoelite.R;
import com.forgeinnovations.android.gitrepoelite.bookmark.BookmarkPresenter;
import com.forgeinnovations.android.gitrepoelite.data.GitHubTopRepoItemAdapter;
import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubTopRepo.GitHubTopRepoResponse;
import com.forgeinnovations.android.gitrepoelite.db.GitHubSearchOpenHelper;
import com.forgeinnovations.android.gitrepoelite.viewmodel.TopRepoViewModel;
import com.rm.rmswitch.RMTristateSwitch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopRepositoryTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopRepositoryTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopRepositoryTab extends Fragment implements GitHubTopRepoItemAdapter.AddTopRepoBookmarkListener {
    private GitHubTopRepoItemAdapter mGitHubTopDevItemAdapter;
    private RecyclerView mTopRepoRecyclerView;

    private ProgressBar mTopRepoTabProgressbar;
    private TextView mTopRepoTabErrorMessage;
    private ImageView mTopRepoTabErrorImage;
    private ShareActionProvider mShareActionProvider;
    private BookmarkPresenter mBookmarkPresenter;
    private String mGithubShareData;
    private TopDeveloperTab.OnFragmentInteractionListener mListener;
    private GitHubSearchOpenHelper mBookmarkDbHelper;
    private HashSet<String> mBookmarkList;

    private int currentSelectedLang;
    private int currentSelectedDuration;
    private RMTristateSwitch mTristateSwitch;
    private TextView mTextViewState;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ListView mListView;
    String[] topLangauges = new String[]{"Python","Java","Javascript","PHP","CPP","CSharp","R","Objective-C","Swift","Matlab","Ruby","TypeScript","VBA","Scala","Visual Basic","Kotlin","GO","Perl"};

    public TopRepositoryTab() {
    }

    public static TopRepositoryTab newInstance(String param1, String param2) {
        TopRepositoryTab fragment = new TopRepositoryTab();

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_repo, container, false);


        currentSelectedDuration = RMTristateSwitch.STATE_MIDDLE;
        currentSelectedLang = 1;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTopRepoRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview_toprepo);
        mTopRepoTabProgressbar = (ProgressBar) getActivity().findViewById(R.id.pb_toprepo_loading_indicator);
        mTopRepoTabErrorMessage = (TextView) getActivity().findViewById(R.id.tv_toprepo_error_message);
        mTopRepoTabErrorImage = (ImageView) getActivity().findViewById(R.id.img_error_message);
        mBookmarkDbHelper =  new GitHubSearchOpenHelper(getActivity());
        mGitHubTopDevItemAdapter = new GitHubTopRepoItemAdapter(getActivity());
        mGitHubTopDevItemAdapter.setAddTopRepoBookmarkListener(this);
        mSwipeRefreshLayout = getActivity().findViewById(R.id.swipeToRefresh);
        mTopRepoTabProgressbar.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mTopRepoRecyclerView.setLayoutManager(linearLayoutManager);

        mTopRepoRecyclerView.setAdapter(mGitHubTopDevItemAdapter);

        mTextViewState = (TextView) getActivity().findViewById(R.id.textViewState);
        mTristateSwitch = (RMTristateSwitch) getActivity().findViewById(R.id.switch_top_duration);
        mTristateSwitch.setState(currentSelectedDuration);

        mTristateSwitch.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(RMTristateSwitch switchView, @RMTristateSwitch.State int state) {
                String currState =  state == RMTristateSwitch.STATE_LEFT ? "Daily" : state == RMTristateSwitch.STATE_MIDDLE ? "Weekly" : "Monthly";
                mTextViewState.setText(currState);
                mTopRepoTabProgressbar.setVisibility(View.VISIBLE);
                mTopRepoRecyclerView.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                currentSelectedDuration = state;
                LoadGitHubTopRepos();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTopRepoTabProgressbar.setVisibility(View.VISIBLE);
                mTopRepoRecyclerView.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
                LoadGitHubTopRepos();

                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            }
        });

        mSwipeRefreshLayout.setVisibility(View.GONE);
        LoadGitHubTopRepos();


    }

    public void showFilterDialog(View view){
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        //builder.setCancelable(true);
        //builder.setPositiveButton(R.string.str_apply,null);
        //builder.setView(mListView);
        //builder.setTitle(R.string.dialogTitle);
        final List<Integer> mSelectedItems = new ArrayList<>();  // Where we track the selected items
        int checkedItem = -1;
        // Set the dialog title
        builder.setTitle(R.string.dialogTitle)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(topLangauges, currentSelectedLang , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         Log.i("dailog",String.valueOf(which));
                        currentSelectedLang = which;
                    }
                })
                // Set the action buttons
                .setPositiveButton(R.string.str_apply, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        mTopRepoRecyclerView.setVisibility(View.GONE);
                        mTopRepoTabProgressbar.setVisibility(View.VISIBLE);
                        LoadGitHubTopRepos();
                    }
                })
                .setNegativeButton(R.string.str_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });




        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void LoadGitHubTopRepos() {
        mTristateSwitch.setEnabled(false);

        TopRepoViewModel model = ViewModelProviders.of(this).get(TopRepoViewModel.class);

        Observer<GitHubTopRepoResponse> observer = new Observer<GitHubTopRepoResponse>() {
            @Override
            public void onChanged(@Nullable GitHubTopRepoResponse topRepoList) {
                if(topRepoList.getErrorMessage() == null){
                    mGithubShareData = topRepoList.GetTopRepoShareData();
                    mGitHubTopDevItemAdapter = new GitHubTopRepoItemAdapter(getActivity());
                    mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                    mTopRepoRecyclerView.setVisibility(View.VISIBLE);
                    mGitHubTopDevItemAdapter.setGitHubRepoDevData(topRepoList);
                    mTopRepoTabProgressbar.setVisibility(View.INVISIBLE);
                    mTopRepoTabErrorMessage.setVisibility(View.INVISIBLE);
                    mTopRepoTabErrorImage.setVisibility(View.INVISIBLE);
                    mTopRepoRecyclerView.setAdapter(mGitHubTopDevItemAdapter);
                    mSwipeRefreshLayout.setRefreshing(false);
                }else {
                    mTopRepoRecyclerView.setVisibility(View.GONE);
                    mTopRepoTabProgressbar.setVisibility(View.INVISIBLE);
                    mTopRepoTabErrorMessage.setVisibility(View.VISIBLE);
                    mTopRepoTabErrorImage.setVisibility(View.VISIBLE);
                    mTopRepoTabErrorMessage.setText("Oops something went wrong,\nTry Again after sometime");
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                mTristateSwitch.setEnabled(true);
                mTopRepoTabProgressbar.setVisibility(View.INVISIBLE);
            }
        };
        String duration =  (mTristateSwitch.getState()== RMTristateSwitch.STATE_LEFT ? "daily" : (mTristateSwitch.getState()== RMTristateSwitch.STATE_MIDDLE?"weekly":"monthly"));
        //TODO: Pass langauage and duration from UI
        String langauge = topLangauges[currentSelectedLang];

        model.GetTopRepos (langauge, duration).observe(this, observer);
    }

    private void updateToprepoFavorite(GitHubTopRepoResponse topRepoList, HashSet<String> bookmarkList) {

        for (com.forgeinnovations.android.gitrepoelite.datamodel.GitHubTopRepo.Item item: topRepoList.getItems()) {
            if(bookmarkList.contains(item.getRepoLink()))
                item.setFavorite(true);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mBookmarkDbHelper!=null)
            mBookmarkDbHelper.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mBookmarkDbHelper!=null)
            mBookmarkDbHelper.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.toprepo, menu);

        // Locate MenuItem with ShareActionProvider
//        MenuItem item = menu.findItem(R.id.menu_toprepo_item_share);
//
//        // Fetch and store ShareActionProvider
//        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
//
//        if (mShareActionProvider != null)
//            mShareActionProvider.setShareIntent(createShareDataIntent());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        switch (itemThatWasClickedId) {

            case R.id.action_filter:
                showFilterDialog(getView());
                break;
//            case R.id.action_toprepo_item_share:
//                shareTopRepo();
//                break;

        }


        return super.onOptionsItemSelected(item);
    }

    private void shareTopRepo() {


    }


    private String CreateShareData(HashMap<Integer, com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.Item> bookmarkData) {
        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append("<html><body>");
        for (com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.Item item : bookmarkData.values()) {
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

    @Override
    public void onAddTopRepoBookmark() {
        onFragmentAddBookmarkListener.onFragmentAddTopRepoBookMark("TopRepo");
    }

    public interface FragmentTopRepoListener {
        // TODO: Update argument type and name
        void onFragmentAddTopRepoBookMark(String tab);
    }

    private FragmentTopRepoListener onFragmentAddBookmarkListener;

    public void setAddTopRepoListener(FragmentTopRepoListener fragmentAddBookmarkListener){
        this.onFragmentAddBookmarkListener = fragmentAddBookmarkListener;
    }

    public FragmentTopRepoListener getAddTopRepoListener(){
        return this.onFragmentAddBookmarkListener;
    }
}
