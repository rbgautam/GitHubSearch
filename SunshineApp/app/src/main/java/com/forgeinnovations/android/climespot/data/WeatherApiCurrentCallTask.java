package com.forgeinnovations.android.climespot.data;

import android.os.AsyncTask;

import com.forgeinnovations.android.climespot.datamodel.WeatherApiRequest;
import com.forgeinnovations.android.climespot.datamodel.current.WeatherApiCurrentResponse;
import com.forgeinnovations.android.climespot.datamodel.fiveday.WeatherApiForecastResponse;
import com.forgeinnovations.android.climespot.datamodel.tenday.WeatherApi10DayForecastResponse;

/**
 * Created by Rahul B Gautam on 4/24/18.
 */
public class WeatherApiCurrentCallTask extends AsyncTask<WeatherApiRequest, Void, WeatherApiCurrentResponse> implements WeatherApiRESTAdapter.AsyncWebResponse{

    private WeatherApiCurrentResponse mWeatherApiCurrentResponse;
    public WeatherApiRESTAdapter.AsyncWebResponse delegate = null;

    WeatherApiRESTAdapter mQueryAdapter;
    //MainPresenter mMainPresenter;


    public WeatherApiCurrentCallTask(WeatherApiRESTAdapter mQueryAdapter) {
        this.mQueryAdapter = mQueryAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected WeatherApiCurrentResponse doInBackground(WeatherApiRequest... weatherApiRequests) {

        mQueryAdapter.delegate = this;

        mQueryAdapter.getCurrentWeatherData(weatherApiRequests[0].city,weatherApiRequests[0].country ,weatherApiRequests[0].state,weatherApiRequests[0].units,weatherApiRequests[0].apiKey);
        return mWeatherApiCurrentResponse;


    }

    @Override
    public void processFinish(WeatherApiCurrentResponse weatherApiCurrentResponse) {

        WeatherApiCurrentResponse apiResponse = weatherApiCurrentResponse;

    }

    @Override
    public void processForecastFinish(WeatherApiForecastResponse output) {

    }

    @Override
    public void process10DayForecastFinish(WeatherApi10DayForecastResponse output) {

    }
}
