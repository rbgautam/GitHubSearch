package com.forgeinnovations.android.climespot.datamodel;

/**
 * Created by Rahul B Gautam on 4/23/18.
 */

import com.squareup.moshi.Json;

import java.util.List;

public class Daily {

    @Json(name = "summary")
    private String summary;
    @Json(name = "icon")
    private String icon;
    @Json(name = "data")
    private List<Datum__> data = null;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Datum__> getData() {
        return data;
    }

    public void setData(List<Datum__> data) {
        this.data = data;
    }

}
