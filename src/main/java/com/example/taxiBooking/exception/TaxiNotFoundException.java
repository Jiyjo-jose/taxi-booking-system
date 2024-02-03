package com.example.taxiBooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaxiNotFoundException extends RuntimeException {
    public TaxiNotFoundException() {
        super(" not available near pickup location");
    }
}