package com.example.taxiBooking.contract.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingRequest {
    private String pickUpLocation;
    private String dropOffLocation;
    private double distance;

}
