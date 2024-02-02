package com.example.taxiBooking.service;

import com.example.taxiBooking.contract.request.BookingRequest;
import com.example.taxiBooking.contract.response.BookingResponse;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.model.Taxi;
import com.example.taxiBooking.model.User;
import com.example.taxiBooking.repository.BookingRepository;
import com.example.taxiBooking.repository.TaxiRepository;
import com.example.taxiBooking.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TaxiRepository taxiRepository;

    public BookingResponse book(BookingRequest request, Long UserId, Long TaxiId, double distance) {
        User  user = userRepository
                .findById(UserId)
                .orElseThrow(
                ()-> new EntityNotFoundException()
        );
        Taxi  taxi =taxiRepository
                .findById(TaxiId)
                .orElseThrow(
                        ()->new EntityNotFoundException()
                );
        Double basicFare= 100.00;
        Double fare = basicFare + ((distance-5)*20.00);
        Booking booking1= Booking.builder()
                .user(user)
                .taxi(taxi)
                .fare(fare)
                .bookingTime(LocalDateTime.now())
                .pickUpLocation(request.getPickUpLocation())
                .dropOffLocation(request.getDropOffLocation())
                .status(true)
                .build();
        bookingRepository.save(booking1);
        return modelMapper.map(booking1,BookingResponse.class);
    }
    public Booking getById(Long id) {
        return bookingRepository
                .findById(id)
                .orElseThrow(
                        ()-> new EntityNotFoundException()
                );
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
