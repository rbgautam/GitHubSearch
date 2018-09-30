package com.forgeinnovations.android.githubelite.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

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

    private static final String PREFERENCES_FILE = "USER_SETTINGS";
    private static final String PREF_USER_FIRST_TIME = "PREF_USER_FIRST_TIME";
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

    public static boolean ShowOnBoarding(Context ctx) {
        return Boolean.valueOf(readSharedSetting(ctx, PREF_USER_FIRST_TIME, "true"));
    }

    public static void SkipOnBoarding(Context ctx) {
        saveSharedSetting(ctx, PREF_USER_FIRST_TIME, "false");
    }


    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static Drawable tintMyDrawable(Drawable drawable, int color) {
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }


}
