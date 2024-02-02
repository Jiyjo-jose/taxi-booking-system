package com.example.taxiBooking.controller;

import com.example.taxiBooking.contract.request.BookingRequest;
import com.example.taxiBooking.contract.request.SignUpRequest;
import com.example.taxiBooking.contract.response.BookingResponse;
import com.example.taxiBooking.contract.response.SignUpResponse;
import com.example.taxiBooking.repository.BookingRepository;
import com.example.taxiBooking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/booking")
    public ResponseEntity<BookingResponse> book(@Valid @RequestBody BookingRequest request , @RequestParam Long userId, @RequestParam Long taxiId, @RequestParam double distance){
        return ResponseEntity.ok(bookingService.book(request,userId,taxiId,distance));
    }
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String>cancelBooking(@PathVariable Long id){
        bookingService.cancelBooking(id);
        return ResponseEntity.ok("booking cancelled");



    }
}
