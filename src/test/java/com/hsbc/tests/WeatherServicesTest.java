/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsbc.tests;

import com.hsbc.Exceptions.EmptyResultException;
import com.hsbc.Exceptions.UnauthorizedRequestException;
import com.hsbc.Service.Impl.WeatherServiceImpl;
import com.hsbc.Service.WeatherService;
import com.hsbc.Utils.WeatherAppUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import junit.framework.Assert;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 *
 * @author Alex Theys
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-app-context.xml")
public class WeatherServicesTest {
    
    @Value("${app.id}")
    private String apiKey;
    private String wrongApiKey = "wrong_key";
    private String cityDoesntExist = "thiscitydoesntexistatall!";
    private JSONObject json = null; 
    
    @Autowired
    WeatherService weatherservice = new WeatherServiceImpl();
    
    @Before
    public void setup() throws MalformedURLException, IOException, UnsupportedEncodingException, UnauthorizedRequestException ,EmptyResultException{
        json = weatherservice.getWeather("london", apiKey);
    }
    
    /**
     * This Test ensure the api key used 
     */
    @Test
    public void testjsonObjectElements() {
        Assert.assertNotNull(json);
        Assert.assertNotNull(weatherservice.getTodaysFormattedDate(json));
        Assert.assertEquals("London", weatherservice.getCityName(json));
        Assert.assertNotNull(weatherservice.getOverallWeatherDescription(json));
        Assert.assertNotNull(weatherservice.getTemperatures(json));
        Assert.assertNotNull(WeatherAppUtils.convertTemparatureToCelsius(weatherservice.getTemperatures(json)));
        Assert.assertNotNull(WeatherAppUtils.convertTemparatureToFahrenheit(weatherservice.getTemperatures(json)));
        Assert.assertNotNull(weatherservice.getSunriseSunset(json, "sunrise"));
        Assert.assertNotNull(weatherservice.getSunriseSunset(json, "sunset"));
        Assert.assertEquals("GB", weatherservice.getTimezoneID(json));
        
        
    }
    
    /**
     * Checks to Ensure the custom Exception is thrown when a bad API Key is sent
     * @throws MalformedURLException
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws UnauthorizedRequestException 
     */
    @Test(expected = UnauthorizedRequestException.class)
    public void testGetWeatherwithWrongAPIKey() throws MalformedURLException, IOException, UnsupportedEncodingException, UnauthorizedRequestException, EmptyResultException {
        JSONObject badJson = weatherservice.getWeather("london", wrongApiKey);
    }
    
    /**
     * Checks to Ensure the custom Exception is thrown when a bad API Key is sent
     * @throws MalformedURLException
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws UnauthorizedRequestException 
     */
    @Test(expected = EmptyResultException.class)
    public void testGetWeatherwithCityDoesntExist() throws MalformedURLException, IOException, UnsupportedEncodingException, UnauthorizedRequestException, EmptyResultException {
        JSONObject badJson = weatherservice.getWeather(cityDoesntExist, apiKey);
    }
}
