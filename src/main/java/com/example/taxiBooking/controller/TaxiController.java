package com.example.taxiBooking.controller;

import com.example.taxiBooking.contract.request.TaxiRequest;
import com.example.taxiBooking.contract.response.TaxiResponse;
import com.example.taxiBooking.service.TaxiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v3")
@RequiredArgsConstructor
public class TaxiController {

    private final TaxiService taxiService;

    @PostMapping("/addTaxi")
    public ResponseEntity<TaxiResponse> addTaxi(@Valid @RequestBody TaxiRequest request) {
        return ResponseEntity.ok(taxiService.addTaxi(request));
    }
}
