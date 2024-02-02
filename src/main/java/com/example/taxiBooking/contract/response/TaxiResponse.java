package com.example.taxiBooking.contract.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxiResponse {
    private Long taxiId;

    private String driverName;
    private String licenseNumber;
    private String currentLocation;
}
