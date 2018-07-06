package com.forgeinnovations.android.githubelite.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.forgeinnovations.android.githubelite.tabs.SearchTab;

/**
 * Created by Rahul B Gautam on 7/4/18.
 */
public class PageAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PageAdapter(FragmentManager fm, int NumberOfTabs) {

        super(fm);
        this.mNoOfTabs = NumberOfTabs;

    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                SearchTab searchTab= new SearchTab();
                return searchTab;
            default:
                return null;
        }


    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
