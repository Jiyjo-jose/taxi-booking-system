package com.example.taxiBooking.exception;


public class TaxiNotFoundException extends RuntimeException {
    public TaxiNotFoundException() {
        super(" not available near pickup location");
    }
}