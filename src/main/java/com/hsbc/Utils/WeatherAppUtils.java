/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsbc.Utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Alex Theys
 * This class aims to collect the generally used function for formatting, and converting temperatures
 * A collection of Utilities
 */
public class WeatherAppUtils {
    
    /**
     * A function that will date a format and a date.
     * @param format
     * @param date
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static String formatDate(String format, Date date) throws IllegalArgumentException, NullPointerException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
        
    }
    
    /**
     * Converts the Kelvin temperature to Celsius
     * @param temperatureInKelvin
     * @return
     * @throws ArithmeticException
     * @throws NullPointerException 
     */
    public static BigDecimal convertTemparatureToCelsius(BigDecimal temperatureInKelvin) throws ArithmeticException, NullPointerException {
        BigDecimal kelvinConversion = new BigDecimal(273.15);
        return temperatureInKelvin.subtract(kelvinConversion).round(new MathContext(4, RoundingMode.CEILING));
    }
    
    /**
     * Converts the Kelvin temperature to Fahrenheit
     * @param temperatureInKelvin
     * @return
     * @throws ArithmeticException
     * @throws NullPointerException 
     */
    public static BigDecimal convertTemparatureToFahrenheit(BigDecimal temperatureInKelvin) throws ArithmeticException, NullPointerException {
        BigDecimal partOne = new BigDecimal(9).divide(new BigDecimal(5));
        return temperatureInKelvin.multiply(partOne).subtract(new BigDecimal(459.67)).round(new MathContext(4, RoundingMode.CEILING));
    }
    
    
}
