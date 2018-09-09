package com.forgeinnovations.android.githubelite.viewmodel.URLService;

import com.forgeinnovations.android.githubelite.datamodel.GitHubSearch.GitHubSeachResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rahul B Gautam on 4/16/18.
 */

public interface GitHubSearchService {
    public final static String GITHUB_BASE_URL = "https://api.github.com";

    public final static String PARAM_QUERY = "q";

    public final static String GITHUB_API_URL = "/search/repositories";

    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    public final static String PARAM_SORT = "sort";
    public final static String sortBy = "stars";


    public final static String GITHUB_QUALIFIED_URL_WITH_SORT = "/search/repositories";//?q={query}&sort=stars";


    @GET(GitHubSearchService.GITHUB_QUALIFIED_URL_WITH_SORT)
    Call<GitHubSeachResponse> getGithubSearchData(@Query("q") String query, @Query("sort") String sort);

}
