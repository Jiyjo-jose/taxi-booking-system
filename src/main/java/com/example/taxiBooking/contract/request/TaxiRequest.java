package com.example.taxiBooking.contract.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaxiRequest {

    @NotBlank(message = "name cannot be blank")
    private String driverName;

    @NotBlank(message = "licenseNumber cannot be blank")
    private String licenseNumber;

    @NotBlank(message = "location cannot be blank")
    private String currentLocation;
}
