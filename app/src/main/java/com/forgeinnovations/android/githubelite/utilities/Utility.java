package com.forgeinnovations.android.githubelite.utilities;

import android.content.Context;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.error.ErrorInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Rahul B Gautam on 9/8/18.
 */
public class Utility {

    Context mContext;
    public Utility(Context mContext) {
        this.mContext = mContext;
    }

    public String getStringValue(int resValue, boolean useSpacer) {
        String result = mContext.getResources().getString(resValue);
        if (useSpacer)
            result = result + " " + mContext.getResources().getString(R.string.Spacer) + " ";
        return result;
    }

    public static OkHttpClient GetOkHttpClient(){

        return new OkHttpClient.Builder()
                       .addInterceptor(
                               new ErrorInterceptor()
                       )
                       .connectTimeout(10, TimeUnit.SECONDS)

                       .writeTimeout(10, TimeUnit.SECONDS)
                       .readTimeout(20, TimeUnit.SECONDS)
                       .retryOnConnectionFailure(true)
                       .build();
    }

    public static Retrofit BuildRetrofitClient(String baseURL, OkHttpClient client ){
        return new Retrofit.Builder()
                       .client(client)
                       .baseUrl(baseURL)
                       .addConverterFactory(MoshiConverterFactory.create())
                       .build();
    }

}
