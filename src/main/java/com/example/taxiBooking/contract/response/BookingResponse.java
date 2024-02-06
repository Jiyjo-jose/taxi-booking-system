package com.example.taxiBooking.contract.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long id;
    private String pickUpLocation;
    private  String dropOffLocation;
    private LocalDateTime bookingTime;
    private double fare;
}
