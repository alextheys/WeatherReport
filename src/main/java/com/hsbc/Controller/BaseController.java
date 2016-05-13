/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
    
    


 */
package com.hsbc.Controller;

import com.hsbc.Exceptions.EmptyResultException;
import com.hsbc.Exceptions.UnauthorizedRequestException;
import com.hsbc.Service.Impl.WeatherServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.hsbc.Service.WeatherService;
import com.hsbc.Utils.WeatherAppUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Controller
public class BaseController {

    private static int counter = 0;
    private static final String VIEW_INDEX = "index";
    private static final String VIEW_DETAILS = "details";
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);

    
    //this variable gets the list of cities from a properties file comma separated
    @Value("${list.cities}")
    private String listOfCities;
    
    //Grabs the appId from the properties file
    @Value("${app.id}")
    private String appId;

    @Autowired
    WeatherService weatherService;

    
    /**
     * This function controls the object of the index page
     * namely the cities to display
     * @param model
     * @return 
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model) {
        List<String> cities = new ArrayList<String>();
        String[] citiesArray = listOfCities.split(",");
        for (String city : citiesArray) {
            cities.add(city);
        }
        model.addAttribute("cities", cities);

        // Spring uses InternalResourceViewResolver and return back index.jsp
        return VIEW_INDEX;

    }

    @RequestMapping(value = "/WeatherReport")
    public String weatherReport(ModelMap model){
        return index(model);
    }
    /**
     * This function handles the display of weather report of the selected city
     * @param city
     * @param model
     * @return 
     */
    @RequestMapping(value = "/WeatherReport/{city}", method = RequestMethod.GET)
    public String weatherReport(@PathVariable String city, ModelMap model) {
        
        try {
            //Call the Weather service to retrieve data from the selected city
            JSONObject json = weatherService.getWeather(city, appId);
            //Add the various objects to be displayed in the view
            model.addAttribute("city", weatherService.getCityName(json));
            model.addAttribute("date", weatherService.getTodaysFormattedDate(json));
            model.addAttribute("overall_description", weatherService.getOverallWeatherDescription(json));
            model.addAttribute("sunrise", weatherService.getSunriseSunset(json, "sunrise"));
            model.addAttribute("sunset", weatherService.getSunriseSunset(json, "sunset"));
            model.addAttribute("temperaturecelsius", WeatherAppUtils.convertTemparatureToCelsius(weatherService.getTemperatures(json)).toPlainString());
            model.addAttribute("temperaturefahrenheit", WeatherAppUtils.convertTemparatureToFahrenheit(weatherService.getTemperatures(json)).toPlainString());
            model.addAttribute("timeZoneID", weatherService.getTimezoneID(json));

        } catch (UnauthorizedRequestException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
            logger.error("An UnauthorizedRequestException occured", ex);
            model.addAttribute("error_message", "It appears that the API you are using is invalid, please contact your administrator to update the key of openweathermap api");
        }catch (EmptyResultException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
            logger.error("An EmptyResultException occured", ex);
            model.addAttribute("error_message", "It appears that the WeatherReport app was unable to find the city you were looking for. Please check the name and try again.");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
            logger.error("An UnsupportedEncodingException occured", ex);
            model.addAttribute("error_message", "It appears that the weather for " + city + " could not be retireved at this time. ");
        } catch (MalformedURLException ex) {
            logger.error("An Exception occured", ex);
            model.addAttribute("error_Message", "It appears that the weather service is unavailable right now.");
        } catch (IOException ex) {
            logger.error("An Exception occured", ex);
            model.addAttribute("error_Message", "It appears that the weather service is unavailable right now.");
        }  catch (Exception ex) {
            //Catching all exceptions in order to display a helpfull message to the user and logging the error
            logger.error("An Exception occured", ex);
            model.addAttribute("error_Message", "It appears that something has gone wrong with the application, please contact you administrators for more information ");
        }
        

        return VIEW_DETAILS;

    }
}
