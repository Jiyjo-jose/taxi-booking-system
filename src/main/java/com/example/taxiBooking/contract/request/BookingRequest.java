package com.example.taxiBooking.contract.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class BookingRequest {
    private String pickUpLocation;
    private String dropOffLocation;
}
