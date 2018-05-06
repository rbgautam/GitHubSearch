package com.forgeinnovations.android.climespot.data;

import android.util.Log;

import com.forgeinnovations.android.climespot.datamodel.RecycleViewItem;
import com.forgeinnovations.android.climespot.datamodel.current.WeatherApiCurrentResponse;
import com.forgeinnovations.android.climespot.datamodel.fiveday.WeatherApiForecastResponse;
import com.forgeinnovations.android.climespot.datamodel.tenday.Datum;
import com.forgeinnovations.android.climespot.datamodel.tenday.WeatherApi10DayForecastResponse;
import com.forgeinnovations.android.climespot.error.ErrorInterceptor;
import com.forgeinnovations.android.climespot.utilities.UrlManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherApiRESTAdapter {

    private final WeatherService weatherService;

    public interface WeatherService{

        @GET(UrlManager.WEATHERAPI_CURRENT_DATA_PATH)
        Call<WeatherApiCurrentResponse> getCurrentWeatherDataForCity(@Query("city") String city, @Query("country") String country, @Query("units") String units, @Query("key") String key );

        @GET(UrlManager.WEATHERAPI_5DAYFORECAST_DATA_PATH)
        Call<WeatherApiForecastResponse> get5dayWeatherDataForCity(@Query("city") String city, @Query("country") String country, @Query("units") String units, @Query("key") String key );

        @GET(UrlManager.WEATHERAPI_10DAYFORECAST_DATA_PATH)
        Call<WeatherApi10DayForecastResponse> get10dayWeatherDataForCity(@Query("city") String city, @Query("country") String country,@Query("units") String units, @Query("key") String key );

    }

    public String urlString = null;

    public WeatherApiRESTAdapter() {
        OkHttpClient client =
                new OkHttpClient.Builder()
                        .addInterceptor(
                                new ErrorInterceptor()
                        )
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(UrlManager.WEATHERAPI_BASE_URL )
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        this.weatherService = retrofit.create(WeatherService.class);
    }


    public interface AsyncWebResponse {
        void processFinish(WeatherApiCurrentResponse output);
        void processForecastFinish(WeatherApiForecastResponse output);
        void process10DayForecastFinish(WeatherApi10DayForecastResponse output);
    }

    public AsyncWebResponse delegate = null;

    public void get10DayWeatherData(final String city, final String country, final String state,final String units, final String apiKey){

        Call<WeatherApi10DayForecastResponse> call = weatherService.get10dayWeatherDataForCity(city, country, units, apiKey);

        Callback<WeatherApi10DayForecastResponse> callback = new Callback<WeatherApi10DayForecastResponse>() {
            WeatherApi10DayForecastResponse forecastResponse = new WeatherApi10DayForecastResponse();

            @Override
            public void onResponse(Call<WeatherApi10DayForecastResponse> call, Response<WeatherApi10DayForecastResponse> response) {
                int statusCode = response.code();
                forecastResponse = response.body();
                delegate.process10DayForecastFinish(forecastResponse);
            }

            @Override
            public void onFailure(Call<WeatherApi10DayForecastResponse> call, Throwable t) {
                Log.i("IOCall","call failed");
                delegate.process10DayForecastFinish(forecastResponse);
            }
        };

        call.enqueue(callback);

    }


    public List<RecycleViewItem> get10DayWeatherForRecycleView(final String city, final String country, final String state,final String units, final String apiKey){

        List<RecycleViewItem> result = null;
        Call<WeatherApi10DayForecastResponse> call = weatherService.get10dayWeatherDataForCity(city, country, units, apiKey);

        Callback<WeatherApi10DayForecastResponse> callback = new Callback<WeatherApi10DayForecastResponse>() {
            WeatherApi10DayForecastResponse forecastResponse = new WeatherApi10DayForecastResponse();

            @Override
            public void onResponse(Call<WeatherApi10DayForecastResponse> call, Response<WeatherApi10DayForecastResponse> response) {
                int statusCode = response.code();
                forecastResponse = response.body();
                //result = getForecastData(forecastResponse);
                delegate.process10DayForecastFinish(forecastResponse);
            }

            @Override
            public void onFailure(Call<WeatherApi10DayForecastResponse> call, Throwable t) {
                Log.i("IOCall","call failed");
                delegate.process10DayForecastFinish(forecastResponse);
            }
        };
        Response<WeatherApi10DayForecastResponse> responseBody = null;
        //call.enqueue(callback);
        try {
            responseBody = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result = getForecastData(responseBody.body(), city,state,country);
        return result;

    }

    private List<RecycleViewItem> getForecastData(WeatherApi10DayForecastResponse forecastResponse,String city, String state, String country ) {
        List<RecycleViewItem> result = new ArrayList<>();

        for (Datum data : forecastResponse.getData()) {
            RecycleViewItem recycleViewItem = new RecycleViewItem();
            recycleViewItem.WeatherDate = data.getTs();
            recycleViewItem.MaxTemp = data.getMaxTemp();
            recycleViewItem.MinTemp = data.getMinTemp();
            recycleViewItem.WeatherDescription = data.getWeather().getDescription();
            recycleViewItem.Datestr = data.getValidDate();
            recycleViewItem.Pressure = data.getPres();
            recycleViewItem.Humidity = data.getRh();
            recycleViewItem.Wind = data.getWindSpd();
            recycleViewItem.WeatherId = data.getWeather().getCode();
            recycleViewItem.WeatherCode = data.getWeather().getCode();
            recycleViewItem.WeatherIcon = data.getWeather().getIcon();

            recycleViewItem.City = city;
            recycleViewItem.State =state;
            recycleViewItem.Country = country;
            result.add(recycleViewItem);

        }


        return result;
    }

    public void get5DayWeatherData(final String city, final String country, final String state,final String units, final String apiKey){

        Call<WeatherApiForecastResponse> call = weatherService.get5dayWeatherDataForCity(city, country, units, apiKey);

        Callback<WeatherApiForecastResponse> callback = new Callback<WeatherApiForecastResponse>() {
            WeatherApiForecastResponse forecastResponse = new WeatherApiForecastResponse();

            @Override
            public void onResponse(Call<WeatherApiForecastResponse> call, Response<WeatherApiForecastResponse> response) {
                int statusCode = response.code();
                forecastResponse = response.body();
                delegate.processForecastFinish(forecastResponse);
            }

            @Override
            public void onFailure(Call<WeatherApiForecastResponse> call, Throwable t) {
                Log.i("IOCall","call failed");
                delegate.processForecastFinish(forecastResponse);
            }
        };

        call.enqueue(callback);

    }

    public void getCurrentWeatherData(final String city, final String country, final String state,final String units  , final String apiKey){

        Call<WeatherApiCurrentResponse> call = weatherService.getCurrentWeatherDataForCity(city,country,units,apiKey);

        Callback<WeatherApiCurrentResponse> callback = new Callback<WeatherApiCurrentResponse>() {

            WeatherApiCurrentResponse apiResponse = new WeatherApiCurrentResponse();

            @Override
            public void onResponse(Call<WeatherApiCurrentResponse> call, Response<WeatherApiCurrentResponse> response) {
                Log.i("IOCall","onresponse");
                int statusCode = response.code();
                apiResponse = response.body();
                urlString = response.raw().networkResponse().request().url().toString();
                delegate.processFinish(apiResponse);

            }

            @Override
            public void onFailure(Call<WeatherApiCurrentResponse> call, Throwable t) {
                Log.i("IOCall","call failed");
                delegate.processFinish(apiResponse);
            }
        };

        call.enqueue(callback);


    }
}
