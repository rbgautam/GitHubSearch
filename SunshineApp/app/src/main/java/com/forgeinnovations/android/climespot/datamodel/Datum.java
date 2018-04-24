package com.forgeinnovations.android.climespot.datamodel;

import com.squareup.moshi.Json;

public class Datum {

    @Json(name = "wind_cdir")
    private String windCdir;
    @Json(name = "rh")
    private Integer rh;
    @Json(name = "pod")
    private String pod;
    @Json(name = "lon")
    private Double lon;
    @Json(name = "pres")
    private Integer pres;
    @Json(name = "timezone")
    private String timezone;
    @Json(name = "ob_time")
    private String obTime;
    @Json(name = "country_code")
    private String countryCode;
    @Json(name = "clouds")
    private Integer clouds;
    @Json(name = "vis")
    private Integer vis;
    @Json(name = "state_code")
    private String stateCode;
    @Json(name = "wind_spd")
    private Double windSpd;
    @Json(name = "lat")
    private Double lat;
    @Json(name = "wind_cdir_full")
    private String windCdirFull;
    @Json(name = "slp")
    private Double slp;
    @Json(name = "datetime")
    private String datetime;
    @Json(name = "ts")
    private Integer ts;
    @Json(name = "station")
    private String station;
    @Json(name = "h_angle")
    private Double hAngle;
    @Json(name = "dewpt")
    private Double dewpt;
    @Json(name = "uv")
    private Double uv;
    @Json(name = "dni")
    private Double dni;
    @Json(name = "wind_dir")
    private Integer windDir;
    @Json(name = "elev_angle")
    private Double elevAngle;
    @Json(name = "ghi")
    private Double ghi;
    @Json(name = "dhi")
    private Double dhi;
    @Json(name = "precip")
    private Object precip;
    @Json(name = "city_name")
    private String cityName;
    @Json(name = "weather")
    private Weather weather;
    @Json(name = "sunset")
    private String sunset;
    @Json(name = "temp")
    private Double temp;
    @Json(name = "sunrise")
    private String sunrise;
    @Json(name = "app_temp")
    private Double appTemp;

    public String getWindCdir() {
        return windCdir;
    }

    public void setWindCdir(String windCdir) {
        this.windCdir = windCdir;
    }

    public Integer getRh() {
        return rh;
    }

    public void setRh(Integer rh) {
        this.rh = rh;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Integer getPres() {
        return pres;
    }

    public void setPres(Integer pres) {
        this.pres = pres;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getObTime() {
        return obTime;
    }

    public void setObTime(String obTime) {
        this.obTime = obTime;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Integer getVis() {
        return vis;
    }

    public void setVis(Integer vis) {
        this.vis = vis;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public Double getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(Double windSpd) {
        this.windSpd = windSpd;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getWindCdirFull() {
        return windCdirFull;
    }

    public void setWindCdirFull(String windCdirFull) {
        this.windCdirFull = windCdirFull;
    }

    public Double getSlp() {
        return slp;
    }

    public void setSlp(Double slp) {
        this.slp = slp;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Double getHAngle() {
        return hAngle;
    }

    public void setHAngle(Double hAngle) {
        this.hAngle = hAngle;
    }

    public Double getDewpt() {
        return dewpt;
    }

    public void setDewpt(Double dewpt) {
        this.dewpt = dewpt;
    }

    public Double getUv() {
        return uv;
    }

    public void setUv(Double uv) {
        this.uv = uv;
    }

    public Double getDni() {
        return dni;
    }

    public void setDni(Double dni) {
        this.dni = dni;
    }

    public Integer getWindDir() {
        return windDir;
    }

    public void setWindDir(Integer windDir) {
        this.windDir = windDir;
    }

    public Double getElevAngle() {
        return elevAngle;
    }

    public void setElevAngle(Double elevAngle) {
        this.elevAngle = elevAngle;
    }

    public Double getGhi() {
        return ghi;
    }

    public void setGhi(Double ghi) {
        this.ghi = ghi;
    }

    public Double getDhi() {
        return dhi;
    }

    public void setDhi(Double dhi) {
        this.dhi = dhi;
    }

    public Object getPrecip() {
        return precip;
    }

    public void setPrecip(Object precip) {
        this.precip = precip;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public Double getAppTemp() {
        return appTemp;
    }

    public void setAppTemp(Double appTemp) {
        this.appTemp = appTemp;
    }

}