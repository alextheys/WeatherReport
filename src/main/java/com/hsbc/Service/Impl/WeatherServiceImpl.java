/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsbc.Service.Impl;

import com.hsbc.Enums.CountryCodesTimeZones;
import com.hsbc.Exceptions.EmptyResultException;
import com.hsbc.Exceptions.UnauthorizedRequestException;
import com.hsbc.Service.WeatherService;
import com.hsbc.Utils.WeatherAppUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *  The service Implementation that handles the retrieval of the JSON as well as the retrieval of the individual objects
 * @author Alex Theys
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    public JSONObject getWeather(String city, String appId) throws UnsupportedEncodingException, MalformedURLException, IOException, UnauthorizedRequestException, EmptyResultException {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(city, "UTF-8") + "&APPID=" + appId;
        URL obj;

        obj = new URL(url);
        //creating the connection using the default GET request
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //check that the request is authorized
        if (con.getResponseCode() == 401) {
            throw new UnauthorizedRequestException("Unauthorised request to the weather API");
        }
        //Gets the input stream and stores it in a buffer reader
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        
        String inputLine;
        //creating a StringBuilder to do the string manipulations
        StringBuilder response = new StringBuilder();

        //reads the lines from the response
        while ((inputLine = in.readLine()) != null) {
            //and appends them in the response object
            response.append(inputLine);
        }
        //closing the connection
        in.close();
        JSONObject jsonObject = new JSONObject(response.toString());
        if(jsonObject.has("cod") && jsonObject.get("cod").equals("404")) {
            throw new EmptyResultException(jsonObject.getString("message"));
            
        }
        
        //returning the JSON Object ath the end of the connection
        return jsonObject;

    }

    /**
     * This function retrieves todays date from the json and formats it dd/MM/yyyy
     * @param returnedFeed
     * @return 
     */
    public String getTodaysFormattedDate(JSONObject returnedFeed) {
        //retrieves the date in UNIX format and sets it in Milliseconds
        Long timeInMillliseconds = returnedFeed.getInt("dt") * 1000L;
        //returns the date formatted as required
        return WeatherAppUtils.formatDate("dd/MM/yyyy", getCalendarwithTimezone(timeInMillliseconds, getLocale(returnedFeed)).getTime());
    }

    /**
     * Retrieves the city's name from the JSON
     * @param returnedFeed
     * @return 
     */
    public String getCityName(JSONObject returnedFeed) {
        return returnedFeed.getString("name");
    }

    /**
     * Retrieves the overall weather at the city
     * @param returnedFeed
     * @return 
     */
    public String getOverallWeatherDescription(JSONObject returnedFeed) {
        JSONArray arr = returnedFeed.getJSONArray("weather");
        String strOverallWeatherDescription = "";
        //checks if the array has at least one item
        if (arr.length() > 0 ) {
            //retrieves the description of the weather
            strOverallWeatherDescription = arr.getJSONObject(0).getString("description");
        }
        return strOverallWeatherDescription;
    }

    /**
     * Retrieves the temperature from the json object (in Kelvin)
     * @param returnedFeed
     * @return 
     */
    public BigDecimal getTemperatures(JSONObject returnedFeed) {
        return returnedFeed.getJSONObject("main").getBigDecimal("temp");
    }

    /**
     * Retrieves the sunset or sunrise from the json object the key can be sunrise or sunset
     * @param returnedFeed
     * @param key
     * @return 
     */
    public String getSunriseSunset(JSONObject returnedFeed, String key) {
        //get the time in UNIX format and sets it in Milliseconds
        Long timeInMillliseconds = returnedFeed.getJSONObject("sys").getInt(key) * 1000L;
        //reurns it in the required format, eg 9:35am; 11:47pm
        return WeatherAppUtils.formatDate("h:mma", getCalendarwithTimezone(timeInMillliseconds, getLocale(returnedFeed)).getTime());
    }

    /**
     * Retrieve the Timezone ID to display next to the sunrise or sunset time
     * @param returnedFeed
     * @return 
     */
    public String getTimezoneID(JSONObject returnedFeed) {

        return getTimezoneID(getLocale(returnedFeed)).name();
    }

    /**
     * Retrieve the country from the json Object
     * @param returnedFeed
     * @return 
     */
    private String getLocale(JSONObject returnedFeed) {
        return returnedFeed.getJSONObject("sys").getString("country");
    }

    /** 
     * retrieves a calendar a sets the timezone, it also localizes the time
     * @param timeInMilliseconds
     * @param locale
     * @return 
     */
    private Calendar getCalendarwithTimezone(Long timeInMilliseconds, String locale) {
        Calendar cal = Calendar.getInstance();
        //set the time coming in milliseconds
        cal.setTimeInMillis(timeInMilliseconds);
        //sets the timezone 
        cal.setTimeZone(TimeZone.getTimeZone(getTimezoneID(locale).getTimeZoneID()));
        //offsets the time depending on the timezone used
        cal.setTimeInMillis(cal.getTimeInMillis() + TimeZone.getTimeZone(getTimezoneID(locale).getTimeZoneID()).getOffset(cal.getTimeInMillis()));
        return cal;
    }

    /**
     * This function retrieves the timezone code from the enum
     * First however it checks in the locale/country is supported, if not it reverts the timezone to GMT
     * @param locale
     * @return 
     */
    private CountryCodesTimeZones getTimezoneID(String locale) {
        CountryCodesTimeZones timeZoneId = CountryCodesTimeZones.GMT;
        //checks if the locale requested is supported
        if (CountryCodesTimeZones.checkIfEnumExists(locale)) {
            //if it is it returns its value
            timeZoneId = CountryCodesTimeZones.valueOf(locale);
        }
        //if not it returns the default GMT value
        return timeZoneId;
    }

}
