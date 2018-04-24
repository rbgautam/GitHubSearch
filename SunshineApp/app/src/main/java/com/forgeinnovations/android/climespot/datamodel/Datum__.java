package com.forgeinnovations.android.climespot.datamodel;

/**
 * Created by Rahul B Gautam on 4/23/18.
 */

import com.squareup.moshi.Json;

public class Datum__ {

    @Json(name = "time")
    private Integer time;
    @Json(name = "summary")
    private String summary;
    @Json(name = "icon")
    private String icon;
    @Json(name = "sunriseTime")
    private Integer sunriseTime;
    @Json(name = "sunsetTime")
    private Integer sunsetTime;
    @Json(name = "moonPhase")
    private Double moonPhase;
    @Json(name = "precipIntensity")
    private Integer precipIntensity;
    @Json(name = "precipIntensityMax")
    private Integer precipIntensityMax;
    @Json(name = "precipIntensityMaxTime")
    private Integer precipIntensityMaxTime;
    @Json(name = "precipProbability")
    private Integer precipProbability;
    @Json(name = "temperatureHigh")
    private Double temperatureHigh;
    @Json(name = "temperatureHighTime")
    private Integer temperatureHighTime;
    @Json(name = "temperatureLow")
    private Double temperatureLow;
    @Json(name = "temperatureLowTime")
    private Integer temperatureLowTime;
    @Json(name = "apparentTemperatureHigh")
    private Double apparentTemperatureHigh;
    @Json(name = "apparentTemperatureHighTime")
    private Integer apparentTemperatureHighTime;
    @Json(name = "apparentTemperatureLow")
    private Double apparentTemperatureLow;
    @Json(name = "apparentTemperatureLowTime")
    private Integer apparentTemperatureLowTime;
    @Json(name = "dewPoint")
    private Double dewPoint;
    @Json(name = "humidity")
    private Double humidity;
    @Json(name = "pressure")
    private Double pressure;
    @Json(name = "windSpeed")
    private Double windSpeed;
    @Json(name = "windGust")
    private Double windGust;
    @Json(name = "windGustTime")
    private Integer windGustTime;
    @Json(name = "windBearing")
    private Integer windBearing;
    @Json(name = "cloudCover")
    private Double cloudCover;
    @Json(name = "uvIndex")
    private Integer uvIndex;
    @Json(name = "uvIndexTime")
    private Integer uvIndexTime;
    @Json(name = "visibility")
    private Double visibility;
    @Json(name = "ozone")
    private Double ozone;
    @Json(name = "temperatureMin")
    private Double temperatureMin;
    @Json(name = "temperatureMinTime")
    private Integer temperatureMinTime;
    @Json(name = "temperatureMax")
    private Double temperatureMax;
    @Json(name = "temperatureMaxTime")
    private Integer temperatureMaxTime;
    @Json(name = "apparentTemperatureMin")
    private Double apparentTemperatureMin;
    @Json(name = "apparentTemperatureMinTime")
    private Integer apparentTemperatureMinTime;
    @Json(name = "apparentTemperatureMax")
    private Double apparentTemperatureMax;
    @Json(name = "apparentTemperatureMaxTime")
    private Integer apparentTemperatureMaxTime;
    @Json(name = "precipType")
    private String precipType;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

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

    public Integer getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(Integer sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public Integer getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(Integer sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public Double getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(Double moonPhase) {
        this.moonPhase = moonPhase;
    }

    public Integer getPrecipIntensity() {
        return precipIntensity;
    }

    public void setPrecipIntensity(Integer precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public Integer getPrecipIntensityMax() {
        return precipIntensityMax;
    }

    public void setPrecipIntensityMax(Integer precipIntensityMax) {
        this.precipIntensityMax = precipIntensityMax;
    }

    public Integer getPrecipIntensityMaxTime() {
        return precipIntensityMaxTime;
    }

    public void setPrecipIntensityMaxTime(Integer precipIntensityMaxTime) {
        this.precipIntensityMaxTime = precipIntensityMaxTime;
    }

    public Integer getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(Integer precipProbability) {
        this.precipProbability = precipProbability;
    }

    public Double getTemperatureHigh() {
        return temperatureHigh;
    }

    public void setTemperatureHigh(Double temperatureHigh) {
        this.temperatureHigh = temperatureHigh;
    }

    public Integer getTemperatureHighTime() {
        return temperatureHighTime;
    }

    public void setTemperatureHighTime(Integer temperatureHighTime) {
        this.temperatureHighTime = temperatureHighTime;
    }

    public Double getTemperatureLow() {
        return temperatureLow;
    }

    public void setTemperatureLow(Double temperatureLow) {
        this.temperatureLow = temperatureLow;
    }

    public Integer getTemperatureLowTime() {
        return temperatureLowTime;
    }

    public void setTemperatureLowTime(Integer temperatureLowTime) {
        this.temperatureLowTime = temperatureLowTime;
    }

    public Double getApparentTemperatureHigh() {
        return apparentTemperatureHigh;
    }

    public void setApparentTemperatureHigh(Double apparentTemperatureHigh) {
        this.apparentTemperatureHigh = apparentTemperatureHigh;
    }

    public Integer getApparentTemperatureHighTime() {
        return apparentTemperatureHighTime;
    }

    public void setApparentTemperatureHighTime(Integer apparentTemperatureHighTime) {
        this.apparentTemperatureHighTime = apparentTemperatureHighTime;
    }

    public Double getApparentTemperatureLow() {
        return apparentTemperatureLow;
    }

    public void setApparentTemperatureLow(Double apparentTemperatureLow) {
        this.apparentTemperatureLow = apparentTemperatureLow;
    }

    public Integer getApparentTemperatureLowTime() {
        return apparentTemperatureLowTime;
    }

    public void setApparentTemperatureLowTime(Integer apparentTemperatureLowTime) {
        this.apparentTemperatureLowTime = apparentTemperatureLowTime;
    }

    public Double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getWindGust() {
        return windGust;
    }

    public void setWindGust(Double windGust) {
        this.windGust = windGust;
    }

    public Integer getWindGustTime() {
        return windGustTime;
    }

    public void setWindGustTime(Integer windGustTime) {
        this.windGustTime = windGustTime;
    }

    public Integer getWindBearing() {
        return windBearing;
    }

    public void setWindBearing(Integer windBearing) {
        this.windBearing = windBearing;
    }

    public Double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(Double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public Integer getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(Integer uvIndex) {
        this.uvIndex = uvIndex;
    }

    public Integer getUvIndexTime() {
        return uvIndexTime;
    }

    public void setUvIndexTime(Integer uvIndexTime) {
        this.uvIndexTime = uvIndexTime;
    }

    public Double getVisibility() {
        return visibility;
    }

    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }

    public Double getOzone() {
        return ozone;
    }

    public void setOzone(Double ozone) {
        this.ozone = ozone;
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public Integer getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public void setTemperatureMinTime(Integer temperatureMinTime) {
        this.temperatureMinTime = temperatureMinTime;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public Integer getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public void setTemperatureMaxTime(Integer temperatureMaxTime) {
        this.temperatureMaxTime = temperatureMaxTime;
    }

    public Double getApparentTemperatureMin() {
        return apparentTemperatureMin;
    }

    public void setApparentTemperatureMin(Double apparentTemperatureMin) {
        this.apparentTemperatureMin = apparentTemperatureMin;
    }

    public Integer getApparentTemperatureMinTime() {
        return apparentTemperatureMinTime;
    }

    public void setApparentTemperatureMinTime(Integer apparentTemperatureMinTime) {
        this.apparentTemperatureMinTime = apparentTemperatureMinTime;
    }

    public Double getApparentTemperatureMax() {
        return apparentTemperatureMax;
    }

    public void setApparentTemperatureMax(Double apparentTemperatureMax) {
        this.apparentTemperatureMax = apparentTemperatureMax;
    }

    public Integer getApparentTemperatureMaxTime() {
        return apparentTemperatureMaxTime;
    }

    public void setApparentTemperatureMaxTime(Integer apparentTemperatureMaxTime) {
        this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
    }

    public String getPrecipType() {
        return precipType;
    }

    public void setPrecipType(String precipType) {
        this.precipType = precipType;
    }

}
