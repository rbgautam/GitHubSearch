package com.forgeinnovations.android.climespot.datamodel;

/**
 * Created by Rahul B Gautam on 4/24/18.
 */
public class WeatherApiRequest {

    public String city;
    public String country;
    public String state;
    public String units;
    public String apiKey;


    public WeatherApiRequest(String city, String country, String state, String units, String apiKey) {
        this.city = city;
        this.country = country;
        this.state = state;
        this.units = units;
        this.apiKey = apiKey;
    }
}
