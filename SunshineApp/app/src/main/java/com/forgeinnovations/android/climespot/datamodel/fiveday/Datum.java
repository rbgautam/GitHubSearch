package com.forgeinnovations.android.climespot.datamodel.fiveday;

import com.squareup.moshi.Json;

public class Datum {

    @Json(name = "wind_cdir")
    private String windCdir;
    @Json(name = "pod")
    private String pod;
    @Json(name = "rh")
    private Integer rh;
    @Json(name = "wind_spd")
    private Double windSpd;
    @Json(name = "pop")
    private Integer pop;
    @Json(name = "timestamp_utc")
    private String timestampUtc;
    @Json(name = "wind_cdir_full")
    private String windCdirFull;
    @Json(name = "app_temp")
    private Double appTemp;
    @Json(name = "wind_dir")
    private Integer windDir;
    @Json(name = "wind_gust_spd")
    private Double windGustSpd;
    @Json(name = "snow_depth")
    private Integer snowDepth;
    @Json(name = "pres")
    private Double pres;
    @Json(name = "clouds_mid")
    private Integer cloudsMid;
    @Json(name = "dewpt")
    private Double dewpt;
    @Json(name = "snow")
    private Integer snow;
    @Json(name = "uv")
    private Double uv;
    @Json(name = "clouds_low")
    private Integer cloudsLow;
    @Json(name = "ozone")
    private Double ozone;
    @Json(name = "weather")
    private Weather weather;
    @Json(name = "clouds_hi")
    private Integer cloudsHi;
    @Json(name = "precip")
    private Double precip;
    @Json(name = "timestamp_local")
    private String timestampLocal;
    @Json(name = "slp")
    private Double slp;
    @Json(name = "ts")
    private Integer ts;
    @Json(name = "datetime")
    private String datetime;
    @Json(name = "temp")
    private Double temp;
    @Json(name = "dhi")
    private Object dhi;
    @Json(name = "clouds")
    private Integer clouds;
    @Json(name = "vis")
    private Double vis;

    public String getWindCdir() {
        return windCdir;
    }

    public void setWindCdir(String windCdir) {
        this.windCdir = windCdir;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public Integer getRh() {
        return rh;
    }

    public void setRh(Integer rh) {
        this.rh = rh;
    }

    public Double getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(Double windSpd) {
        this.windSpd = windSpd;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

    public String getTimestampUtc() {
        return timestampUtc;
    }

    public void setTimestampUtc(String timestampUtc) {
        this.timestampUtc = timestampUtc;
    }

    public String getWindCdirFull() {
        return windCdirFull;
    }

    public void setWindCdirFull(String windCdirFull) {
        this.windCdirFull = windCdirFull;
    }

    public Double getAppTemp() {
        return appTemp;
    }

    public void setAppTemp(Double appTemp) {
        this.appTemp = appTemp;
    }

    public Integer getWindDir() {
        return windDir;
    }

    public void setWindDir(Integer windDir) {
        this.windDir = windDir;
    }

    public Double getWindGustSpd() {
        return windGustSpd;
    }

    public void setWindGustSpd(Double windGustSpd) {
        this.windGustSpd = windGustSpd;
    }

    public Integer getSnowDepth() {
        return snowDepth;
    }

    public void setSnowDepth(Integer snowDepth) {
        this.snowDepth = snowDepth;
    }

    public Double getPres() {
        return pres;
    }

    public void setPres(Double pres) {
        this.pres = pres;
    }

    public Integer getCloudsMid() {
        return cloudsMid;
    }

    public void setCloudsMid(Integer cloudsMid) {
        this.cloudsMid = cloudsMid;
    }

    public Double getDewpt() {
        return dewpt;
    }

    public void setDewpt(Double dewpt) {
        this.dewpt = dewpt;
    }

    public Integer getSnow() {
        return snow;
    }

    public void setSnow(Integer snow) {
        this.snow = snow;
    }

    public Double getUv() {
        return uv;
    }

    public void setUv(Double uv) {
        this.uv = uv;
    }

    public Integer getCloudsLow() {
        return cloudsLow;
    }

    public void setCloudsLow(Integer cloudsLow) {
        this.cloudsLow = cloudsLow;
    }

    public Double getOzone() {
        return ozone;
    }

    public void setOzone(Double ozone) {
        this.ozone = ozone;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Integer getCloudsHi() {
        return cloudsHi;
    }

    public void setCloudsHi(Integer cloudsHi) {
        this.cloudsHi = cloudsHi;
    }

    public Double getPrecip() {
        return precip;
    }

    public void setPrecip(Double precip) {
        this.precip = precip;
    }

    public String getTimestampLocal() {
        return timestampLocal;
    }

    public void setTimestampLocal(String timestampLocal) {
        this.timestampLocal = timestampLocal;
    }

    public Double getSlp() {
        return slp;
    }

    public void setSlp(Double slp) {
        this.slp = slp;
    }

    public Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Object getDhi() {
        return dhi;
    }

    public void setDhi(Object dhi) {
        this.dhi = dhi;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Double getVis() {
        return vis;
    }

    public void setVis(Double vis) {
        this.vis = vis;
    }

}