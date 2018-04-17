package com.example.android.datafrominternet.utilities;

/**
 * Created by Rahul B Gautam on 4/16/18.
 */

public class URLManager {
    public final static String GITHUB_BASE_URL = "https://api.github.com";

    public final static String PARAM_QUERY = "q";

    public final static String GITHUB_API_URL =  "/search/repositories";

    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    public final static String PARAM_SORT = "sort";
    public final static String sortBy = "stars";


    public final static String GITHUB_QUALIFIED_URL_WITH_SORT = "/search/repositories";//?q={query}&sort=stars";
}
