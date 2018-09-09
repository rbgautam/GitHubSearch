package com.forgeinnovations.android.githubelite.viewmodel.URLService;


import com.forgeinnovations.android.githubelite.datamodel.GitHubTopRepo.GitHubTopRepoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rahul B Gautam on 9/8/18.
 */
public interface GitHubTopRepoService {
    static final String BASE_URL = "https://trendings.herokuapp.com";
    static final String BASE_URL_PARAM = "/repo";
    //?lang=java&since=daily";
    static final String PARAM_LANGUAGE = "lang";
    static final String PARAM_DURATION = "since";

    @GET(GitHubTopRepoService.BASE_URL_PARAM)
    Call<GitHubTopRepoResponse> getGithubRepoData(@Query(GitHubTopRepoService.PARAM_LANGUAGE) String language, @Query(GitHubTopRepoService.PARAM_DURATION) String since);
}
