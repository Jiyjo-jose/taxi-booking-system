package com.example.taxiBooking.contract.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long id;
    private String pickUpLocation;

    private  String dropOffLocation;
    private LocalDateTime bookingTime;
}
