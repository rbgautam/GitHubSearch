package com.forgeinnovations.android.gitrepoelite.utilities;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Rahul B Gautam on 11/18/18.
 */
public class BookmarkLinearLayoutManager extends LinearLayoutManager {

    /**
     * Disable predictive animations. There is a bug in RecyclerView which causes views that
     * are being reloaded to pull invalid ViewHolders from the internal recycler stack if the
     * adapter size has decreased since the ViewHolder was recycled.
     */
    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
            //setAutoMeasureEnabled(false);
        } catch (IndexOutOfBoundsException e) {
            Log.e("Error", "IndexOutOfBoundsException in RecyclerView happens");
        }
    }

    public BookmarkLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        //setAutoMeasureEnabled(false);
    }

    public BookmarkLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        //setAutoMeasureEnabled(false);
    }

    public BookmarkLinearLayoutManager(Context context) {
        super(context);
        //setAutoMeasureEnabled(false);
    }

}

