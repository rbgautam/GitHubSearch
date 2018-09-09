package com.forgeinnovations.android.githubelite.data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rahul B Gautam on 4/20/18.
 */
public class GitHubTopRepoItemAdapter extends RecyclerView.Adapter<GitHubTopRepoItemAdapter.GitHubTopRepoAdapterViewHolder> {

    /* The context we use to utility methods, app resources and layout inflaters */
    private final Context mContext;
    public GitHubTopRepoAdapterViewHolder mGitHubTopRepoAdapterViewHolder;
    private GitHubTopRepoResponse mGitHubTopRepoData;


    public GitHubTopRepoItemAdapter(Context mContext) {
        this.mContext = mContext;

    }

    public void setGitHubRepoDevData(GitHubTopRepoResponse gitHubTopRepoResponse) {
        if (gitHubTopRepoResponse.getErrorMessage() == null) {
            mGitHubTopRepoData = gitHubTopRepoResponse;
            Log.i("bookmark trace", String.format("book mark count = %d, gitHubSeachResponse = %d", getItemCount(), gitHubTopRepoResponse.getCount()));
            notifyDataSetChanged();
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
        Log.i("OnBindViewholder", String.valueOf(position));
        Log.i("bookmark trace", String.format("book mark count = %d", getItemCount()));
        if (position < getItemCount()) {
            //androidLog.i("OnBindViewholder",String.valueOf(position));
            ArrayList<Item> topRepoList = new ArrayList<>(mGitHubTopRepoData.getItems());
            Item item = topRepoList.get(position);

            String starsAdded =   String.format("(%s)",topRepoList.get(position).getAddedStars());
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

        if (mGitHubTopRepoData == null || mGitHubTopRepoData.getErrorMessage() !=null)
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

    public class GitHubTopRepoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mStarsAdded;
        private final TextView mRepoName;
        private final TextView mRepoDesc;
        private final TextView mRepoLang;
        private final TextView mRepoStars;
        private final TextView mRepoForks;
        private final LinearLayout mAvatarlinearLayout;

        public GitHubTopRepoAdapterViewHolder(View itemView) {
            super(itemView);
            mStarsAdded = (TextView) itemView.findViewById(R.id.tr_stars_added);
            mRepoName = (TextView) itemView.findViewById(R.id.tr_repo_name);
            mRepoDesc = (TextView) itemView.findViewById(R.id.tr_repo_desc);
            mRepoLang = (TextView) itemView.findViewById(R.id.tr_repo_lang);
            mRepoStars = (TextView) itemView.findViewById(R.id.tr_repo_stars_count);
            mRepoForks = (TextView) itemView.findViewById(R.id.tr_forks_count);
            //mAvatarImageview = (ImageView) itemView.findViewById(R.id.avtar);
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


    }
}
