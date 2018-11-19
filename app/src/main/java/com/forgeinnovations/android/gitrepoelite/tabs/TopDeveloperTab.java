package com.forgeinnovations.android.gitrepoelite.tabs;


import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.forgeinnovations.android.gitrepoelite.R;
import com.forgeinnovations.android.gitrepoelite.bookmark.BookmarkPresenter;

import com.forgeinnovations.android.gitrepoelite.data.GitHubTopDevItemAdapter;
import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.Item;
import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubTopDev.GitHubTopDevResponse;
import com.forgeinnovations.android.gitrepoelite.viewmodel.TopDevViewModel;
import com.rm.rmswitch.RMTristateSwitch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rahul B Gautam on 9/8/18.
 */
public class TopDeveloperTab extends Fragment {

    private GitHubTopDevItemAdapter mGitHubTopDevItemAdapter;
    private RecyclerView mTopDeveloperRecyclerView;
    private ProgressBar mTopDevTabProgressbar;
    private ShareActionProvider mShareActionProvider;
    private TextView mtopDevErrorMessage;
    private BookmarkPresenter mBookmarkPresenter;
    private String mGithubShareData;
    private OnFragmentInteractionListener mListener;
    private int currentSelectedLang;
    private int currentSelectedDuration;
    private RMTristateSwitch mTristateSwitch;
    private TextView mTextViewState;
    private String[] topLangauges = new String[]{"Python","Java","Javascript","PHP","CPP","CSharp","R","Objective-C","Swift","Matlab","Ruby","TypeScript","VBA","Scala","Visual Basic","Kotlin","GO","Perl"};

    public TopDeveloperTab() {
    }

    public static TopDeveloperTab newInstance(String param1, String param2) {
        TopDeveloperTab fragment = new TopDeveloperTab();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topdev, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTopDeveloperRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview_topdev);
        mTopDevTabProgressbar = (ProgressBar) getActivity().findViewById(R.id.pb_topdev_loading_indicator);

        mTopDevTabProgressbar.setVisibility(View.VISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mTopDeveloperRecyclerView.setLayoutManager(linearLayoutManager);

        mGitHubTopDevItemAdapter = new GitHubTopDevItemAdapter(getActivity());

        mTopDeveloperRecyclerView.setAdapter(mGitHubTopDevItemAdapter);
        mtopDevErrorMessage = (TextView) getActivity().findViewById(R.id.tv_topdev_error_message);
        mTextViewState = (TextView) getActivity().findViewById(R.id.textDevViewState);

        mTristateSwitch = (RMTristateSwitch) getActivity().findViewById(R.id.switch_toprepo_duration);
        mTristateSwitch.setState(RMTristateSwitch.STATE_MIDDLE);
        mTristateSwitch.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(RMTristateSwitch switchView, @RMTristateSwitch.State int state) {
                String currState =  state == RMTristateSwitch.STATE_LEFT ? "Daily" : state == RMTristateSwitch.STATE_MIDDLE ? "Weekly" : "Monthly";
                mTextViewState.setText(currState);
                mTopDevTabProgressbar.setVisibility(View.VISIBLE);
                LoadGitHubTopDevs();
            }
        });

        currentSelectedLang = 1;

        LoadGitHubTopDevs();
    }

    private void LoadGitHubTopDevs() {
        mTristateSwitch.setEnabled(false);
        mTopDeveloperRecyclerView.setVisibility(View.GONE);
        TopDevViewModel model = ViewModelProviders.of(this).get(TopDevViewModel.class);

        Observer<GitHubTopDevResponse> observer = new Observer<GitHubTopDevResponse>() {
            @Override
            public void onChanged(@Nullable GitHubTopDevResponse topDevList) {
                mTristateSwitch.setEnabled(true);
                if(topDevList.getErrorMessage() == null) {
                    mGitHubTopDevItemAdapter = new GitHubTopDevItemAdapter(getActivity());
                    mGitHubTopDevItemAdapter.setGitHubTopDevData(topDevList);
                    mTopDevTabProgressbar.setVisibility(View.INVISIBLE);
                    mTopDeveloperRecyclerView.setVisibility(View.VISIBLE);
                    mTopDeveloperRecyclerView.setAdapter(mGitHubTopDevItemAdapter);
                    mtopDevErrorMessage.setVisibility(View.INVISIBLE);
                }else{
                    mTopDeveloperRecyclerView.setVisibility(View.INVISIBLE);
                    mTopDevTabProgressbar.setVisibility(View.INVISIBLE);
                    mtopDevErrorMessage.setVisibility(View.VISIBLE);
                    mtopDevErrorMessage.setText(R.string.error_message);
                }


            }




        };

        String duration =  (mTristateSwitch.getState()== RMTristateSwitch.STATE_LEFT ? "daily" : (mTristateSwitch.getState()== RMTristateSwitch.STATE_MIDDLE?"weekly":"monthly"));
        //TODO: Pass langauage and duration from UI
        String langauge = topLangauges[currentSelectedLang];

        model.GetTopDevs(langauge, duration).observe(this, observer);
    }

    public void showFilterDialog(View view){
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());

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
                        mTopDeveloperRecyclerView.setVisibility(View.GONE);
                        mTopDevTabProgressbar.setVisibility(View.VISIBLE);
                        LoadGitHubTopDevs();
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

        inflater.inflate(R.menu.toprepo, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        if (mShareActionProvider != null)
            mShareActionProvider.setShareIntent(createShareDataIntent());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        switch (itemThatWasClickedId) {

            case R.id.action_filter:
                showFilterDialog(getView());
                break;
//            case R.id.action_toprepo_item_share:
//                shareBookmarks();
//                break;

        }


        return super.onOptionsItemSelected(item);
    }

    private void shareBookmarks() {
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
