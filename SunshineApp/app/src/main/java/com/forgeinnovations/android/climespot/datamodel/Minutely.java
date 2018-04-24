package com.forgeinnovations.android.climespot.datamodel;

/**
 * Created by Rahul B Gautam on 4/23/18.
 */

import java.util.List;

import com.squareup.moshi.Json;

public class Minutely {

    @Json(name = "summary")
    private String summary;
    @Json(name = "icon")
    private String icon;
    @Json(name = "data")
    private List<Datum> data = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}