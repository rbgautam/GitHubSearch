package com.forgeinnovations.android.githubelite.data;

import android.util.Log;

import com.forgeinnovations.android.githubelite.datamodel.GitHubSeachResponse;
import com.forgeinnovations.android.githubelite.error.ErrorInterceptor;
import com.forgeinnovations.android.githubelite.utilities.URLManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rahul B Gautam on 4/16/18.
 */

public class GitHubRestAdapter {

    private final GitHubService gitHubService;

    public String urlString = null;

    public GitHubRestAdapter() {
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
                .baseUrl(URLManager.GITHUB_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        this.gitHubService = retrofit.create(GitHubService.class);
    }

    //sort=stars

    public interface GitHubService{

        @GET(URLManager.GITHUB_QUALIFIED_URL_WITH_SORT)
        Call<GitHubSeachResponse> getGithubSearchData(@Query("q") String query, @Query("sort") String sort);

    }

    public interface AsyncWebResponse {
        void processFinish(GitHubSeachResponse output);
    }

    public AsyncWebResponse delegate = null;

    public void getGitHubData(final String keyword){


        Call<GitHubSeachResponse> call = gitHubService.getGithubSearchData(keyword,"stars");




        Callback<GitHubSeachResponse> callBack = new Callback<GitHubSeachResponse>() {

            public GitHubSeachResponse searchData =  new GitHubSeachResponse();



            @Override
            public void onResponse(Call<GitHubSeachResponse> call, Response<GitHubSeachResponse> response) {
                Log.i("IOCALL","inside onResponse");
                int statusCode = response.code();
                searchData = response.body();
                urlString = response.raw().networkResponse().request().url().toString();
                delegate.processFinish(searchData);
            }

            @Override
            public void onFailure(Call<GitHubSeachResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("IOCALL","Call failed");
                delegate.processFinish(searchData);
            }
        };

        call.enqueue(callBack);


    }
}
