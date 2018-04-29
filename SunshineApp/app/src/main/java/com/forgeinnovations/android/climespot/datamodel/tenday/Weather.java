package com.forgeinnovations.android.climespot.datamodel.tenday;

import com.squareup.moshi.Json;

public class Weather {

    @Json(name = "icon")
    private String icon;
    @Json(name = "code")
    private Integer code;
    @Json(name = "description")
    private String description;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}