package com.forgeinnovations.android.gitrepoelite.datamodel.GitHubTopLanguage;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by Rahul B Gautam on 9/8/18.
 */
public class All {
    @Json(name = "popular")
    private List<Popular> popular = null;
    @Json(name = "all")
    private List<All> all = null;

    public List<Popular> getPopular() {
        return popular;
    }

    public void setPopular(List<Popular> popular) {
        this.popular = popular;
    }

    public List<All> getAll() {
        return all;
    }

    public void setAll(List<All> all) {
        this.all = all;
    }
}
