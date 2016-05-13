/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsbc.Exceptions;

/**
 * Throwing a custom Exception is no results are returned
 * @author Alex Theys
 */
public class EmptyResultException  extends Exception {
    public EmptyResultException(String message) {
        super(message);
    }
}
