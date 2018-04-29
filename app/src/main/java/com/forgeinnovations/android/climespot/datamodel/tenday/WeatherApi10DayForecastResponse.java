package com.forgeinnovations.android.climespot.datamodel.tenday;

import com.squareup.moshi.Json;

import java.util.List;

public class WeatherApi10DayForecastResponse {

    @Json(name = "data")
    private List<Datum> data = null;
    @Json(name = "city_name")
    private String cityName;
    @Json(name = "lon")
    private String lon;
    @Json(name = "timezone")
    private String timezone;
    @Json(name = "lat")
    private String lat;
    @Json(name = "country_code")
    private String countryCode;
    @Json(name = "state_code")
    private String stateCode;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

}