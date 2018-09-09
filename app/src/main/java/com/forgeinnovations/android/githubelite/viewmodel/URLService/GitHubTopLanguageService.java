package com.forgeinnovations.android.githubelite.viewmodel.URLService;


import com.forgeinnovations.android.githubelite.datamodel.GitHubTopLanguage.GitHubTopLangResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Rahul B Gautam on 9/8/18.
 * https://github-trending-api.now.sh/languages
 */
public interface GitHubTopLanguageService {

    static final String BASE_URL = "https://github-trending-api.now.sh/";
    static final String PARAM_LANGUAGE = "languages";

    @GET(GitHubTopLanguageService.PARAM_LANGUAGE)
    Call<GitHubTopLangResponse> getGithubLangData();

}
