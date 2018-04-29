package com.forgeinnovations.android.climespot.datamodel.current;

/**
 * Created by Rahul B Gautam on 4/23/18.
 */


import com.squareup.moshi.Json;

import java.util.List;

public class WeatherApiCurrentResponse {

    @Json(name = "data")
    private List<Datum> data = null;
    @Json(name = "count")
    private Integer count;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
