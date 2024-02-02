package com.example.taxiBooking.service;

import com.example.taxiBooking.contract.request.BookingRequest;
import com.example.taxiBooking.contract.response.BookingResponse;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;

    public BookingResponse book(BookingRequest request) {
        Double basicFare= 100.00;
        Double fare = basicFare + (request.getDistance()*20.00);
        Booking booking1= Booking.builder()
                .fare(fare)
                .bookingTime(LocalDateTime.now())
                .pickUpLocation(request.getPickUpLocation())
                .dropOffLocation(request.getDropOffLocation())
                .status(true)
                .build();
        bookingRepository.save(booking1);
//        Booking booking1 = modelMapper.map(request,Booking.class);
//        Booking booking1=bookingRepository.save(booking);
        return modelMapper.map(booking1,BookingResponse.class);
    }


    public void cancelBooking(Long id) {
        Booking booking = bookingRepository
                .findById(id)
                .orElseThrow(
                        ()->new EntityNotFoundException("booking not found")
                );
        booking.setStatus(false);
        bookingRepository.save(booking);
    }
}
