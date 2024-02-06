package com.example.taxiBooking.contract.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TaxiResponse {
    private Long taxiId;

    private String driverName;
    private String licenseNumber;
    private String currentLocation;


}
