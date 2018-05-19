package com.forgeinnovations.android.climespot.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

import com.example.android.sunshine.R;
import com.forgeinnovations.android.climespot.data.LocationValidatorTask;
import com.forgeinnovations.android.climespot.datamodel.LocationItem;

/**
 * Created by Rahul B Gautam on 4/10/18.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<LocationItem> {
    private static final int ID_LOCATION_LOADER = 12;

    private static String oldlocationData = null;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);


//        findPreference("location").setOnPreferenceChangeListener(
//                new Preference.OnPreferenceChangeListener() {
//
//                    @Override
//                    public boolean onPreferenceChange(Preference preference, Object newValue) {
//                        boolean result = false;
//
//                        SharedPreferences savedSession = sharedPreferences;
//                        String value = sharedPreferences.getString(preference.getKey(), "");
//
//                        LoaderManager loaderManager = getLoaderManager();
//
//                        //TODO: Validate location then save to preferences
//                        Bundle args = new Bundle();
//                        args.putString("LOCATION_ADDRESS", value);
//
//                        loaderManager.initLoader(ID_LOCATION_LOADER, args, getContext());
//
//
//                        return result;
//                    }
//
//                });

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();

        oldlocationData = sharedPreferences.getString("location", "");
        int countPref = prefScreen.getPreferenceCount();

        for (int i = 0; i < countPref; i++) {
            Preference p = prefScreen.getPreference(i);
            // You don't need to set up preference summaries for checkbox preferences because
            // they are already set up in xml using summaryOff and summary On
            if (!(p instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, value);
            }
        }

    }


    // COMPLETED (2) Create a setPreferenceSummary which takes a Preference and String value as parameters.
    // This method should check if the preference is a ListPreference and, if so, find the label
    // associated with the value. You can do this by using the findIndexOfValue and getEntries methods
    // of Preference.

    /**
     * Updates the summary for the preference
     *
     * @param preference The preference to be updated
     * @param value      The value that the preference was updated to
     */
    private void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            // For list preferences, figure out the label of the selected value
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                // Set the summary to that label
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference != null) {
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }


            if(key.equals("location")){
                SharedPreferences savedSession =  sharedPreferences ;
                String value = sharedPreferences.getString(preference.getKey(), "");

                LoaderManager loaderManager = getLoaderManager();

                //TODO: Validate location then save to preferences
                Bundle args = new Bundle();
                args.putString("LOCATION_ADDRESS",value);

                loaderManager.initLoader(ID_LOCATION_LOADER, args,  this);


            }

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<LocationItem> onCreateLoader(int id, final Bundle args) {
        final Context mContext = getContext();
        return new LocationValidatorTask(mContext, args);

    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<LocationItem> loader, LocationItem data) {
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        if (data != null) {


            String locationData = data.city + "," + data.country;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("location", locationData);
            editor.commit();
        } else {


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("location", oldlocationData);
            editor.commit();
            Toast.makeText(getContext(), "Location not found!!", Toast.LENGTH_LONG);
        }

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<LocationItem> loader) {

    }
}
