package com.forgeinnovations.android.climespot.error;

import android.util.Log;

import java.io.IOException;

/**
 * Created by Rahul B Gautam on 4/24/18.
 */
public class ApiResponseError extends IOException {

    private int responseCode;
    private String message;

    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public String getMessage() {
        return message;
    }


    public ApiResponseError(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;

        Log.e("IOCALL", message);
    }
}
