package com.example.taxiBooking.contract.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BookingResponse {

    private Long id;
    private String pickUpLocation;
    private String dropOffLocation;
    private LocalDateTime bookingTime;
    private double fare;
}
