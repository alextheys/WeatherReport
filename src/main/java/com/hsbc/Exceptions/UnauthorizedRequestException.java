/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsbc.Exceptions;

/**
 * An exception class to handles unauthorized requests to the weather API
 * @author Alex Theys
 */
public class UnauthorizedRequestException extends Exception {
    public UnauthorizedRequestException(String message) {
        super(message);
    }
    
}
