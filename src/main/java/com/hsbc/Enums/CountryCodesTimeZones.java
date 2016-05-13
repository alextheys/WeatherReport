/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsbc.Enums;

/**
 * This enum gathers the supported timezones of the application
 *
 * @author Alex Theys
 */
public enum CountryCodesTimeZones {
    HK("Asia/Hong_Kong"), GB("Europe/London"), GMT("GMT");

    private String timeZoneID;
    //this variable is required to enable check if the country code is in the enum and therefore supported
    private static final CountryCodesTimeZones[] copyOfTZ = values();
    
    /**
     * Constructor
     * @param t 
     */
    private CountryCodesTimeZones(String t) {
        timeZoneID = t;
    }

    //getter
    public String getTimeZoneID() {
        return timeZoneID;
    }

    /**
     * A function the checks if the requested country code is supported
     * @param code
     * @return 
     */
    public static Boolean checkIfEnumExists(String code) {
        for (CountryCodesTimeZones value : copyOfTZ) {
            if (value.name().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
