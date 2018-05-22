package com.forgeinnovations.android.githubelite.datamodel;

import com.squareup.moshi.Json;

import java.util.List;

public class GitHubSeachResponse {

    @Json(name = "total_count")
    private Integer totalCount;
    @Json(name = "incomplete_results")
    private Boolean incompleteResults;
    @Json(name = "items")
    private List<Item> items = null;



    public Integer getTotalCount() {
        return totalCount;
    }

    public Integer getSearchCount() {
        return getItems().size();
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;

    }




}