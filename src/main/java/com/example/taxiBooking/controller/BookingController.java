package com.example.taxiBooking.controller;

import com.example.taxiBooking.contract.request.BookingRequest;
import com.example.taxiBooking.contract.response.BookingResponse;
import com.example.taxiBooking.contract.response.TaxiResponse;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/booking")
    public ResponseEntity<BookingResponse> book(@Valid @RequestBody BookingRequest request , @RequestParam Long userId, @RequestParam Long taxiId, @RequestParam double distance){
        return ResponseEntity.ok(bookingService.book(request,userId,taxiId,distance));
    }
    @GetMapping("/{id}/viewBooking")
    public Booking getById(@PathVariable Long id){
        return bookingService.getById(id);
    }
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id){
        bookingService.cancelBooking(id);
        return ResponseEntity.ok("booking cancelled");
    }
    @GetMapping("/searchTaxi")
    public List<TaxiResponse> availableTaxi( @RequestParam String pickupLocation) {
        return bookingService.availableTaxi( pickupLocation);
    }



}
