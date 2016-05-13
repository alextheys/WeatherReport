/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsbc.Service;

import com.hsbc.Exceptions.EmptyResultException;
import com.hsbc.Exceptions.UnauthorizedRequestException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import org.json.*;


/**
 *
 * @author Alex Theys
 */
public interface WeatherService {

    //retireve the main JSON Object
    public JSONObject getWeather(String city, String appId) throws UnsupportedEncodingException, MalformedURLException, IOException, UnauthorizedRequestException, EmptyResultException;

    //returns the date of the weather report
    public String getTodaysFormattedDate(JSONObject returnedFeed);

    //returns the city name
    public String getCityName(JSONObject returnedFeed);

    // returns the overall description of the weather (e.g. "Light rain", "Clear sky", etc.)
    public String getOverallWeatherDescription(JSONObject returnedFeed);

    //get the temperature from the api (in Kelvin)
    public BigDecimal getTemperatures(JSONObject returnedFeed);

    //get the sunrise or sunset times in 12 hour format (e.g. 9:35am; 11:47pm)
    public String getSunriseSunset(JSONObject returnedFeed, String key);
    
    //get the timezone id
    public String getTimezoneID(JSONObject returnedFeed);

}
