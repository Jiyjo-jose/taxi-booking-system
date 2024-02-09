package com.example.taxiBooking.exception;

public class LowAccountBalanceException extends RuntimeException {
    public LowAccountBalanceException() {
        super(" you don't have enough money for the ride sorry ");
    }
}
