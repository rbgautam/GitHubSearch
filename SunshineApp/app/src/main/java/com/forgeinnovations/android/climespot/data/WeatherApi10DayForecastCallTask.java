package com.forgeinnovations.android.climespot.data;

import android.os.AsyncTask;

import com.forgeinnovations.android.climespot.datamodel.WeatherApiRequest;
import com.forgeinnovations.android.climespot.datamodel.current.WeatherApiCurrentResponse;
import com.forgeinnovations.android.climespot.datamodel.fiveday.WeatherApiForecastResponse;
import com.forgeinnovations.android.climespot.datamodel.tenday.WeatherApi10DayForecastResponse;

/**
 * Created by Rahul B Gautam on 4/28/18.
 */
public class WeatherApi10DayForecastCallTask extends AsyncTask<WeatherApiRequest, Void, WeatherApi10DayForecastResponse> implements WeatherApiRESTAdapter.AsyncWebResponse{

    private WeatherApi10DayForecastResponse mWeatherApiForecastResponse;
    public WeatherApiRESTAdapter.AsyncWebResponse delegate = null;

    WeatherApiRESTAdapter mQueryAdapter;
    //MainPresenter mMainPresenter;


    public WeatherApi10DayForecastCallTask(WeatherApiRESTAdapter mQueryAdapter) {
        this.mQueryAdapter = mQueryAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected WeatherApi10DayForecastResponse doInBackground(WeatherApiRequest... weatherApiRequests) {

        mQueryAdapter.delegate = this;

        mQueryAdapter.get10DayWeatherData(weatherApiRequests[0].city,weatherApiRequests[0].country ,weatherApiRequests[0].state,weatherApiRequests[0].units,weatherApiRequests[0].apiKey);
        return mWeatherApiForecastResponse;


    }

    @Override
    public void processFinish(WeatherApiCurrentResponse weatherApiForecastResponse) {



    }

    @Override
    public void processForecastFinish(WeatherApiForecastResponse weatherApiForecastResponse) {

    }

    @Override
    public void process10DayForecastFinish(WeatherApi10DayForecastResponse weatherApi10DayForecastResponse) {
        WeatherApi10DayForecastResponse apiResponse = weatherApi10DayForecastResponse;
    }
}
