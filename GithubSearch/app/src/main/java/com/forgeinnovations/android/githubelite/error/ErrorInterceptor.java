package com.forgeinnovations.android.githubelite.error;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 *
 * Interceptors allow us to grab Requests and Responses between Layers of Implementation. In simpler
 * terms:
 * - Checks Requests prior to being sent over the Network
 * - Checks Responses prior to when our GitHubRestAdapter calls back to the Repository.
 *
 * Among other useful things (see OkHttp Docs for more info), we can perform some global error
 * handling operations. Errors will still be propogated to onErrorReturn() in the Repository.
 * Created by Rahul B Gautam on 4/16/18.
 */

public class ErrorInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (!response.isSuccessful()) {
            throw new GitHubError(
                    response.code(),
                    response.message()
            );
        }
        return response;
    }
}
