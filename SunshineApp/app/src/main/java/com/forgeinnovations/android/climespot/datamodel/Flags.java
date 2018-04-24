package com.forgeinnovations.android.climespot.datamodel;

/**
 * Created by Rahul B Gautam on 4/23/18.
 */

import com.squareup.moshi.Json;

import java.util.List;

public class Flags {

    @Json(name = "sources")
    private List<String> sources = null;
    @Json(name = "isd-stations")
    private List<String> isdStations = null;
    @Json(name = "units")
    private String units;

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public List<String> getIsdStations() {
        return isdStations;
    }

    public void setIsdStations(List<String> isdStations) {
        this.isdStations = isdStations;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

}
