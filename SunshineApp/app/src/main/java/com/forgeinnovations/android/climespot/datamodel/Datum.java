package com.forgeinnovations.android.climespot.datamodel;

/**
 * Created by Rahul B Gautam on 4/23/18.
 */

import com.squareup.moshi.Json;

public class Datum {

    @Json(name = "time")
    private Integer time;
    @Json(name = "precipIntensity")
    private Integer precipIntensity;
    @Json(name = "precipProbability")
    private Integer precipProbability;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getPrecipIntensity() {
        return precipIntensity;
    }

    public void setPrecipIntensity(Integer precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public Integer getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(Integer precipProbability) {
        this.precipProbability = precipProbability;
    }

}
