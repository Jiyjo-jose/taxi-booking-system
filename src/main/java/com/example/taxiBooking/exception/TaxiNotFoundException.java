package com.example.taxiBooking.exception;


public class TaxiNotFoundException extends RuntimeException {
    public TaxiNotFoundException(String message) {
        super(message);
    }
}