package com.forgeinnovations.android.githubelite.error;

import android.util.Log;

import java.io.IOException;

/**
 * Created by Rahul B Gautam on 4/16/18.
 */

public class GitHubError extends IOException {
    private int responseCode;
    private String message;

    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public String getMessage() {
        return message;
    }


    public GitHubError(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;

        Log.e("IOCALL", message);
    }
}
