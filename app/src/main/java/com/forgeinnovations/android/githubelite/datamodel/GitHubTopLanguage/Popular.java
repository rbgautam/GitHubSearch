package com.forgeinnovations.android.githubelite.datamodel.GitHubTopLanguage;

import com.squareup.moshi.Json;

/**
 * Created by Rahul B Gautam on 9/8/18.
 */
public class Popular {
    @Json(name = "urlParam")
    private String urlParam;
    @Json(name = "name")
    private String name;

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
