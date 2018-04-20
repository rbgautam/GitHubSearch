package com.example.android.datafrominternet.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.datafrominternet.R;
import com.example.android.datafrominternet.datamodel.GitHubSeachResponse;
import com.example.android.datafrominternet.datamodel.Item;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by Rahul B Gautam on 4/20/18.
 */
public class GitHubListItemAdapter extends RecyclerView.Adapter<GitHubListItemAdapter.GitHubListAdapterViewHolder> {

    private GitHubSeachResponse mGitHubData;

    /* The context we use to utility methods, app resources and layout inflaters */
    private final Context mContext;

    public GitHubListItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setGitHubData(GitHubSeachResponse gitHubSeachResponse){
        mGitHubData = gitHubSeachResponse;
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
        Log.i("OnBindViewholder",String.valueOf(position));
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

    public class GitHubListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView mReponameTextView;
        private final TextView mRepoDescTextView;
        private final TextView mWatcherCountTextView;
        private final TextView mForksCountTextView;
        private final TextView mStarsCountTextView;
        private final ImageView mAvatarImageview;


        public GitHubListAdapterViewHolder(View itemView) {
            super(itemView);
            mReponameTextView = (TextView) itemView.findViewById(R.id.repo_name);
            mRepoDescTextView = (TextView) itemView.findViewById(R.id.repo_desc);

            mWatcherCountTextView = (TextView) itemView.findViewById(R.id.watchers_count);
            mForksCountTextView = (TextView) itemView.findViewById(R.id.forks_count);
            mStarsCountTextView = (TextView) itemView.findViewById(R.id.stars_count);
            mAvatarImageview = (ImageView) itemView.findViewById(R.id.avtar);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            //String weatherForDay = mWeatherData[adapterPosition];

        }
    }
}
