package com.forgeinnovations.android.climespot.data;

import com.forgeinnovations.android.climespot.datamodel.WeatherApiResponse;
import com.forgeinnovations.android.climespot.utilities.UrlManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherApiRESTAdapter {

    private final WeatherService weatherService;

    public interface WeatherService{

        @GET(UrlManager.WEATHERAPI_CURRENT_DATA_PATH)
        Call<WeatherApiResponse> getWaetherDataForCity(@Query("city") String city, @Query("country") String country,@Query("key") String key );

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

        this.WeatherService = retrofit.create(WeatherService.class);
    }
}
