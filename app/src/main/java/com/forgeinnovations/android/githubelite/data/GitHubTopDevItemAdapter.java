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
import android.widget.TextView;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.datamodel.GitHubTopDev.GitHubTopDevResponse;
import com.forgeinnovations.android.githubelite.datamodel.GitHubTopDev.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rahul B Gautam on 4/20/18.
 */
public class GitHubTopDevItemAdapter extends RecyclerView.Adapter<GitHubTopDevItemAdapter.GitHubTopDevAdapterViewHolder> {

    private GitHubTopDevResponse mGitHubTopDevData;

    /* The context we use to utility methods, app resources and layout inflaters */
    private final Context mContext;

    public GitHubTopDevAdapterViewHolder mGitHubTopDevAdapterViewHolder;


    public GitHubTopDevItemAdapter(Context mContext) {
        this.mContext = mContext;

    }

    public void setGitHubTopDevData(GitHubTopDevResponse gitHubTopDevResponse){
        mGitHubTopDevData = gitHubTopDevResponse;
        Log.i("bookmark trace",String.format("book mark count = %d, gitHubSeachResponse = %d",getItemCount(), gitHubTopDevResponse.getCount() ));
        notifyDataSetChanged();
        //notifyItemRangeChanged(0,getItemCount()-1);

    }

    @Override
    public GitHubTopDevAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutListItem = R.layout.github_topdev_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutListItem,parent,false);
        mGitHubTopDevAdapterViewHolder = new GitHubTopDevAdapterViewHolder(view);
        return mGitHubTopDevAdapterViewHolder;
    }


    @Override
    public void onBindViewHolder(GitHubTopDevAdapterViewHolder holder, int position) {
        Log.i("OnBindViewholder",String.valueOf(position));
        Log.i("bookmark trace",String.format("book mark count = %d",getItemCount()));
        if(position < getItemCount()) {
            //androidLog.i("OnBindViewholder",String.valueOf(position));
            ArrayList<Item> topDevList = new ArrayList<>(mGitHubTopDevData.getItems());
            Item item =  topDevList.get(position);
            String name = topDevList.get(position).getUser();
            String fullName = topDevList.get(position).getFullName();
            String userLink = topDevList.get(position).getUserLink();


            holder.mUsernameTextView.setText(name);
            holder.mUserFullNameTextView .setText(fullName);
            holder.mUserLinkTextView.setText(userLink);

            Picasso.get().load(item.getDeveloperAvatar()).into(holder.mAvatarImageview);
        }

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {

        if(mGitHubTopDevData == null)
            return 0;
        int totCount = mGitHubTopDevData.getCount();

        return  totCount;
    }

    public class GitHubTopDevAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView mUsernameTextView;
        private final TextView mUserFullNameTextView;
        private final ImageView mAvatarImageview;
        private final TextView mUserLinkTextView;


        public GitHubTopDevAdapterViewHolder(View itemView) {
            super(itemView);
            mUsernameTextView = (TextView) itemView.findViewById(R.id.td_user);
            mUserFullNameTextView = (TextView) itemView.findViewById(R.id.td_full_name);
            mAvatarImageview = (ImageView) itemView.findViewById(R.id.avtar);
            mUserLinkTextView = (TextView) itemView.findViewById(R.id.td_user_link);
            itemView.setOnClickListener(this);
        }




        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            //String weatherForDay = mWeatherData[adapterPosition];
            Item item = new ArrayList<Item>(mGitHubTopDevData.getItems()).get(adapterPosition);
            Log.i("onclick",item.getUserLink());
            Item favItem = item;

            //TODO: Convert Item to string and save to db
            //After spinning off an AsysncTask
            openWebPage(item.getUserLink(),view);


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
