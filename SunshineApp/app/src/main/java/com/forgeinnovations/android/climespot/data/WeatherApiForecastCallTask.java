package com.forgeinnovations.android.climespot.data;

import android.os.AsyncTask;

import com.forgeinnovations.android.climespot.datamodel.WeatherApiRequest;
import com.forgeinnovations.android.climespot.datamodel.current.WeatherApiCurrentResponse;
import com.forgeinnovations.android.climespot.datamodel.fiveday.WeatherApiForecastResponse;
import com.forgeinnovations.android.climespot.datamodel.tenday.WeatherApi10DayForecastResponse;

/**
 * Created by Rahul B Gautam on 4/28/18.
 */
public class WeatherApiForecastCallTask extends AsyncTask<WeatherApiRequest, Void, WeatherApiForecastResponse> implements WeatherApiRESTAdapter.AsyncWebResponse{

    private WeatherApiForecastResponse mWeatherApiForecastResponse;
    public WeatherApiRESTAdapter.AsyncWebResponse delegate = null;

    WeatherApiRESTAdapter mQueryAdapter;
    //MainPresenter mMainPresenter;


    public WeatherApiForecastCallTask(WeatherApiRESTAdapter mQueryAdapter) {
        this.mQueryAdapter = mQueryAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected WeatherApiForecastResponse doInBackground(WeatherApiRequest... weatherApiRequests) {

        mQueryAdapter.delegate = this;

        mQueryAdapter.get5DayWeatherData(weatherApiRequests[0].city,weatherApiRequests[0].country ,weatherApiRequests[0].state,weatherApiRequests[0].units,weatherApiRequests[0].apiKey);
        return mWeatherApiForecastResponse;


    }

    @Override
    public void processFinish(WeatherApiCurrentResponse weatherApiForecastResponse) {



    }

    @Override
    public void processForecastFinish(WeatherApiForecastResponse weatherApiForecastResponse) {
        WeatherApiForecastResponse apiResponse = weatherApiForecastResponse;
    }

    @Override
    public void process10DayForecastFinish(WeatherApi10DayForecastResponse output) {

    }
}
