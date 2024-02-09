package com.example.taxiBooking.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException() {
        super("user with this email already exist");
    }
}
