package com.forgeinnovations.android.githubelite.viewmodel.URLService;

import com.forgeinnovations.android.githubelite.datamodel.GitHubTopDev.GitHubTopDevResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rahul B Gautam on 9/8/18.
 */
public interface GitHubTopDevService {
    static final String BASE_URL = "https://trendings.herokuapp.com";
    static final String BASE_URL_PARAM = "/developer";
    //?lang=java&since=daily";
    static final String PARAM_LANGUAGE = "lang";
    static final String PARAM_DURATION = "since";

    @GET(GitHubTopDevService.BASE_URL_PARAM)
    Call<GitHubTopDevResponse> getGithubDevData(@Query(GitHubTopDevService.PARAM_LANGUAGE) String language, @Query(GitHubTopDevService.PARAM_DURATION) String since);

}
