package com.forgeinnovations.android.githubelite.data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.datamodel.GitHubTopRepo.GitHubTopRepoResponse;
import com.forgeinnovations.android.githubelite.datamodel.GitHubTopRepo.Item;
import com.forgeinnovations.android.githubelite.db.DataManager;
import com.forgeinnovations.android.githubelite.db.GitHubSearchOpenHelper;
import com.forgeinnovations.android.githubelite.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Rahul B Gautam on 4/20/18.
 */
public class GitHubTopRepoItemAdapter extends RecyclerView.Adapter<GitHubTopRepoItemAdapter.GitHubTopRepoAdapterViewHolder> {

    /* The context we use to utility methods, app resources and layout inflaters */
    private final Context mContext;
    public GitHubTopRepoAdapterViewHolder mGitHubTopRepoAdapterViewHolder;
    public AddTopRepoBookmarkListener onAddTopRepoListener;
    private GitHubTopRepoResponse mGitHubTopRepoData;
    private GitHubSearchOpenHelper mDbOpenHelper;
    private HashSet<String> mRepoBookmarkList;

    public GitHubTopRepoItemAdapter(Context mContext) {
        this.mContext = mContext;
        this.mDbOpenHelper = new GitHubSearchOpenHelper(mContext);
        getRepoBookmarks();
    }

    private void getRepoBookmarks() {
        AsyncTask<Void, Void, HashSet<String>> asyncTask = new AsyncTask<Void, Void, HashSet<String>>() {
            @Override
            protected HashSet<String> doInBackground(Void... voids) {
                DataManager dm = DataManager.getSingletonInstance();
                mRepoBookmarkList = dm.getBookmarks(mDbOpenHelper, "TOPREPODATA");
                return mRepoBookmarkList;
            }


        };

        asyncTask.execute();
    }

    public void setGitHubRepoDevData(GitHubTopRepoResponse gitHubTopRepoResponse) {
        if (gitHubTopRepoResponse.getErrorMessage() == null) {
            mGitHubTopRepoData = gitHubTopRepoResponse;
            if(mRepoBookmarkList!=null)
            {
                updateFavoriteItems(gitHubTopRepoResponse.getItems());
            }
            Log.i("bookmark trace", String.format("book mark count = %d, gitHubSeachResponse = %d", getItemCount(), gitHubTopRepoResponse.getCount()));
            notifyDataSetChanged();
        }
    }

    private void updateFavoriteItems(List<Item> items) {
        for (Item item:items) {
            if(mRepoBookmarkList.contains(item.getRepoLink()))
                item.setFavorite(true);
        }
    }

    @Override
    public GitHubTopRepoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutListItem = R.layout.github_toprepo_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutListItem, parent, false);
        mGitHubTopRepoAdapterViewHolder = new GitHubTopRepoAdapterViewHolder(view);
        return mGitHubTopRepoAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(GitHubTopRepoAdapterViewHolder holder, int position) {
        Log.i("OnBindViewtoprepo", String.valueOf(position));
        Log.i("top repo trace", String.format("book mark count = %d", getItemCount()));
        if (position < getItemCount()) {
            //androidLog.i("OnBindViewholder",String.valueOf(position));
            ArrayList<Item> topRepoList = new ArrayList<>(mGitHubTopRepoData.getItems());
            Item item = topRepoList.get(position);

            String starsAdded = String.format("(%s)", topRepoList.get(position).getAddedStars());
            String repoName = topRepoList.get(position).getRepo();
            String description = topRepoList.get(position).getDesc();
            String repoLink = topRepoList.get(position).getRepoLink();
            String forks = topRepoList.get(position).getForks();
            String stars = topRepoList.get(position).getStars();
            String language = topRepoList.get(position).getLang();

            holder.mStarsAdded.setText(starsAdded);
            holder.mRepoName.setText(repoName);
            holder.mRepoDesc.setText(description);
            holder.mRepoStars.setText(stars);
            holder.mRepoForks.setText(forks);
            holder.mRepoLang.setText(language);

            if (mRepoBookmarkList!= null && mRepoBookmarkList.contains(item.getRepoLink())) {
                holder.mBookmarkIcon.setImageResource(R.drawable.ic_action_bookmarkadded);
            }

            for (int x = 0; x < item.getAvatars().size(); x++) {
                ImageView image = new ImageView(mContext);
                Picasso.get().load(item.getAvatars().get(x)).resize(100, 100).into(image);
                holder.mAvatarlinearLayout.addView(image);
            }
        }

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {

        if (mGitHubTopRepoData == null || mGitHubTopRepoData.getErrorMessage() != null)
            return 0;

        int totCount = mGitHubTopRepoData.getCount();

        return totCount;
    }

    /**
     * This method fires off an implicit Intent to open a webpage.
     *
     * @param url Url of webpage to open. Should start with http:// or https:// as that is the
     *            scheme of the URI expected with this Intent according to the Common Intents page
     */
    private void openWebPage(String url, View view) {
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

    public AddTopRepoBookmarkListener getAddTopRepoBookmarkListener() {
        return onAddTopRepoListener;
    }

    public void setAddTopRepoBookmarkListener(AddTopRepoBookmarkListener onAddBookmarkListener) {
        this.onAddTopRepoListener = onAddBookmarkListener;
    }

    public interface AddTopRepoBookmarkListener {

        void onAddTopRepoBookmark();
    }

    public class GitHubTopRepoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mStarsAdded;
        private final TextView mRepoName;
        private final TextView mRepoDesc;
        private final TextView mRepoLang;
        private final TextView mRepoStars;
        private final TextView mRepoForks;
        private final LinearLayout mAvatarlinearLayout;
        private final LinearLayout mBookmarkContainer;
        private final ImageView mBookmarkIcon;

        private View.OnClickListener bookmarkOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBookmark(v);
            }
        };

        public GitHubTopRepoAdapterViewHolder(View itemView) {
            super(itemView);
            mStarsAdded = (TextView) itemView.findViewById(R.id.tr_stars_added);
            mRepoName = (TextView) itemView.findViewById(R.id.tr_repo_name);
            mRepoDesc = (TextView) itemView.findViewById(R.id.tr_repo_desc);
            mRepoLang = (TextView) itemView.findViewById(R.id.tr_repo_lang);
            mRepoStars = (TextView) itemView.findViewById(R.id.tr_repo_stars_count);
            mRepoForks = (TextView) itemView.findViewById(R.id.tr_forks_count);
            mBookmarkContainer = (LinearLayout) itemView.findViewById(R.id.addRepoBookmark);
            mBookmarkIcon = (ImageView) itemView.findViewById(R.id.addRepoBookmarkImage);

            mBookmarkContainer.setOnClickListener(bookmarkOnClickListener);

            mAvatarlinearLayout = (LinearLayout) itemView.findViewById(R.id.avatarlinearLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            //String weatherForDay = mWeatherData[adapterPosition];
            Item item = new ArrayList<Item>(mGitHubTopRepoData.getItems()).get(adapterPosition);
            Log.i("onclick", item.getRepoLink());
            Item favItem = item;

            //TODO: Convert Item to string and save to db
            //After spinning off an AsysncTask
            openWebPage(item.getRepoLink(), view);


        }


        public void AddBookmark(View view) {
            int adapterPosition = getAdapterPosition();
            if (mGitHubTopRepoData.getItems().size() == 0)
                return;
            final AddTopRepoBookmarkListener listener = getAddTopRepoBookmarkListener();
            final com.forgeinnovations.android.githubelite.datamodel.GitHubTopRepo.Item item = mGitHubTopRepoData.getItems().get(adapterPosition);
            Log.i("onclick", item.getRepoLink());
            final com.forgeinnovations.android.githubelite.datamodel.GitHubTopRepo.Item favItem = item;

            if (mRepoBookmarkList.contains(item.getRepoLink()))
                return;

            AsyncTask<Void, Void, Long> newTask = new AsyncTask<Void, Void, Long>() {
                @Override
                protected Long doInBackground(Void... voids) {
                    //TODO: Convert Item to string and save to db
                    String dataJSON = NetworkUtils.ConvertToRepoJSON(favItem);

                    DataManager dm = DataManager.getSingletonInstance();
                    long result = dm.saveRepoBookmark(mDbOpenHelper, dataJSON, item.getRepoLink(), item.getLang(), -1, "TOPREPODATA");
                    //After spinning off an AsysncTask
                    return result;
                }

                /**
                 * <p>Runs on the UI thread after {@link #doInBackground}. The
                 * specified result is the value returned by {@link #doInBackground}.</p>
                 * <p>
                 * <p>This method won't be invoked if the task was cancelled.</p>
                 *
                 * @param result The result of the operation computed by {@link #doInBackground}.
                 * @see #onPreExecute
                 * @see #doInBackground
                 * @see #onCancelled(Object)
                 */
                @Override
                protected void onPostExecute(Long result) {
                    super.onPostExecute(result);
                    if (result > -1) {
                        Log.i("bookmark trace", "on post execute save bookmark");
                        mBookmarkIcon.setImageResource(R.drawable.ic_action_bookmarkadded);

                        //listener.onAddTopRepoBookmark();
                    }

                }
            };

            newTask.execute();

        }


    }
}
