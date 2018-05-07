package com.forgeinnovations.android.climespot.data;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.forgeinnovations.android.climespot.datamodel.LocationItem;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rahul B Gautam on 5/6/18.
 */
public class LocationValidatorTask extends AsyncTaskLoader<LocationItem> {


    public Context mContext;
    private Bundle mArgs;

    public LocationValidatorTask(Context context,Bundle args) {
        super(context);
        mContext = context;
        mArgs =args;

    }

    /**
     * Subclasses must implement this to take care of loading their data,
     * as per {@link #startLoading()}.  This is not called by clients directly,
     * but as a result of a call to {@link #startLoading()}.
     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    /**
     * Called on a worker thread to perform the actual load and to return
     * the result of the load operation.
     * <p>
     * Implementations should not deliver the result directly, but should return them
     * from this method, which will eventually end up calling {@link #deliverResult} on
     * the UI thread.  If implementations need to process the results on the UI thread
     * they may override {@link #deliverResult} and do so there.
     * <p>
     * To support cancellation, this method should periodically check the value of
     * {@link #isLoadInBackgroundCanceled} and terminate when it returns true.
     * Subclasses may also override {@link #cancelLoadInBackground} to interrupt the load
     * directly instead of polling {@link #isLoadInBackgroundCanceled}.
     * <p>
     * When the load is canceled, this method may either return normally or throw
     * {@link OperationCanceledException}.  In either case, the {@link Loader} will
     * call {@link #onCanceled} to perform post-cancellation cleanup and to dispose of the
     * result object, if any.
     *
     * @return The result of the load operation.
     * @throws OperationCanceledException if the load is canceled during execution.
     * @see #isLoadInBackgroundCanceled
     * @see #cancelLoadInBackground
     * @see #onCanceled
     */
    @Override
    public LocationItem loadInBackground() {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses = null;

        String name = mArgs.getString("LOCATION_ADDRESS");
        try {
            addresses = geocoder.getFromLocationName(name, 1);

        } catch (IOException e) {
            String errorMessage = "Service not available";
            Log.e("LOCATION_VALIDATE", errorMessage, e);
        }

        if(addresses.size() == 0)
            return null;

        Address addData = addresses.get(0);
        LocationItem locaData = GetLocationData(addData);

        return locaData;
    }

    private LocationItem GetLocationData(Address address) {




        LocationItem result =  new LocationItem();

        if(address == null)
            return result;
        String addressName ="";
        for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressName +=   address.getLocality() + ", " + address.getCountryCode();
            result.city = address.getLocality();
            result.country = address.getCountryCode();
        }


        return result;
    }
}
