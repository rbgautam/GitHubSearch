package com.forgeinnovations.android.githubelite.datamodel.GitHubTopRepo;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by Rahul B Gautam on 9/8/18.
 */
public class GitHubTopRepoResponse {
    @Json(name = "count")
    private Integer count;
    @Json(name = "items")
    private List<Item> items = null;
    private String errorMessage;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
