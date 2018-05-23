package com.forgeinnovations.android.githubelite.data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.datamodel.GitHubSeachResponse;
import com.forgeinnovations.android.githubelite.datamodel.Item;
import com.forgeinnovations.android.githubelite.db.DataManager;
import com.forgeinnovations.android.githubelite.db.GitHubSearchOpenHelper;
import com.forgeinnovations.android.githubelite.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rahul B Gautam on 4/20/18.
 */
public class GitHubListItemAdapter extends RecyclerView.Adapter<GitHubListItemAdapter.GitHubListAdapterViewHolder> {

    private GitHubSeachResponse mGitHubData;

    private String mKeyWord;

    private GitHubSearchOpenHelper mDbOpenHelper;
    /* The context we use to utility methods, app resources and layout inflaters */
    private final Context mContext;


    public GitHubListItemAdapter(Context mContext) {
        this.mContext = mContext;
        this.mDbOpenHelper = new GitHubSearchOpenHelper(mContext);


    }

    public void setGitHubData(GitHubSeachResponse gitHubSeachResponse, String keyword){
        mGitHubData = gitHubSeachResponse;
        mKeyWord = keyword;
        notifyDataSetChanged();
    }
    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public GitHubListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutListItem = R.layout.github_repos_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutListItem,parent,false);



        return new GitHubListAdapterViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(GitHubListAdapterViewHolder holder, int position) {
        //Log.i("OnBindViewholder",String.valueOf(position));
        if(position < getItemCount()) {
            //androidLog.i("OnBindViewholder",String.valueOf(position));

            Item item = mGitHubData.getItems().get(position);
            String name = mGitHubData.getItems().get(position).getName();
            String description = mGitHubData.getItems().get(position).getDescription();

            holder.mReponameTextView.setText(name);
            holder.mRepoDescTextView.setText(description);

            holder.mWatcherCountTextView.setText(String.valueOf(item.getWatchersCount()));
            holder.mForksCountTextView.setText(String.valueOf(item.getForksCount()));
            holder.mStarsCountTextView.setText(String.valueOf(item.getStargazersCount()));

            Picasso.get().load(item.getOwner().getAvatarUrl()).into(holder.mAvatarImageview);
        }

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(mGitHubData == null)
            return 0;
        int totCount = mGitHubData.getSearchCount();

        return  totCount;
    }

    public class GitHubListAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
        private final TextView mReponameTextView;
        private final TextView mRepoDescTextView;
        private final TextView mWatcherCountTextView;
        private final TextView mForksCountTextView;
        private final TextView mStarsCountTextView;
        private final ImageView mAvatarImageview;

        private final LinearLayout mBookmarkContainer;



        public GitHubListAdapterViewHolder(View itemView) {
            super(itemView);
            mReponameTextView = (TextView) itemView.findViewById(R.id.repo_name);
            mRepoDescTextView = (TextView) itemView.findViewById(R.id.repo_desc);

            mWatcherCountTextView = (TextView) itemView.findViewById(R.id.watchers_count);
            mForksCountTextView = (TextView) itemView.findViewById(R.id.forks_count);
            mStarsCountTextView = (TextView) itemView.findViewById(R.id.stars_count);
            mAvatarImageview = (ImageView) itemView.findViewById(R.id.avtar);
            mBookmarkContainer = (LinearLayout) itemView.findViewById(R.id.addBookmark);

            mBookmarkContainer.setOnClickListener(bookmarkOnClickListener);

            itemView.setOnClickListener(this);
        }

        private View.OnClickListener bookmarkOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBookmark(v);
            }
        };

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            //String weatherForDay = mWeatherData[adapterPosition];
            final Item item = mGitHubData.getItems().get(adapterPosition);
            Log.i("onclick",item.getHtmlUrl());
            final Item favItem = item;

//            AsyncTask<Void,Void,Void> newTask = new AsyncTask<Void, Void, Void>() {
//                @Override
//                protected Void doInBackground(Void... voids) {
//                    //TODO: Convert Item to string and save to db
//                    String dataJSON = NetworkUtils.ConvertToJSON(favItem);
//
//                    DataManager dm = DataManager.getSingletonInstance();
//                    dm.saveBookmark(mDbOpenHelper,dataJSON,item.getId().toString(),mKeyWord);
//                    //After spinning off an AsysncTask
//                    return null;
//                }
//            };
//
//            newTask.execute();


            openWebPage(item.getHtmlUrl(),view);


        }

        public void AddBookmark(View view){
            int adapterPosition = getAdapterPosition();
            //String weatherForDay = mWeatherData[adapterPosition];
            final Item item = mGitHubData.getItems().get(adapterPosition);
            Log.i("onclick",item.getHtmlUrl());
            final Item favItem = item;

            AsyncTask<Void,Void,Void> newTask = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    //TODO: Convert Item to string and save to db
                    String dataJSON = NetworkUtils.ConvertToJSON(favItem);

                    DataManager dm = DataManager.getSingletonInstance();
                    dm.saveBookmark(mDbOpenHelper,dataJSON,item.getId().toString(),mKeyWord);
                    //After spinning off an AsysncTask
                    return null;
                }
            };

            newTask.execute();

        }

    }

    /**
     * This method fires off an implicit Intent to open a webpage.
     *
     * @param url Url of webpage to open. Should start with http:// or https:// as that is the
     *            scheme of the URI expected with this Intent according to the Common Intents page
     */
    private void openWebPage(String url,View view) {
        // COMPLETED (2) Use Uri.parse to parse the String into a Uri
        /*
         * We wanted to demonstrate the Uri.parse method because its usage occurs frequently. You
         * could have just as easily passed in a Uri as the parameter of this method.
         */
        Uri webpage = Uri.parse(url);


        // COMPLETED (3) Create an Intent with Intent.ACTION_VIEW and the webpage Uri as parameters
        /*
         * Here, we create the Intent with the action of ACTION_VIEW. This action allows the user
         * to view particular content. In this case, our webpage URL.
         */
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        // COMPLETED (4) Verify that this Intent can be launched and then call startActivity
        /*
         * This is a check we perform with every implicit Intent that we launch. In some cases,
         * the device where this code is running might not have an Activity to perform the action
         * with the data we've specified. Without this check, in those cases your app would crash.
         */


        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
             mContext.startActivity(intent);
        }
    }
}
