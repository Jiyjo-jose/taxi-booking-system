package com.example.taxiBooking.service;

import com.example.taxiBooking.contract.request.TaxiRequest;
import com.example.taxiBooking.contract.response.TaxiResponse;
import com.example.taxiBooking.model.Taxi;
import com.example.taxiBooking.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxiService {
    private final ModelMapper modelMapper;
    private final TaxiRepository taxiRepository;

    public TaxiResponse addTaxi(TaxiRequest request) {

        Taxi taxi =
                Taxi.builder()
                        .driverName(request.getDriverName())
                        .licenseNumber(request.getLicenseNumber())
                        .currentLocation(request.getCurrentLocation())
                        .build();
        taxiRepository.save(taxi);
        return modelMapper.map(taxi, TaxiResponse.class);
    }
}
