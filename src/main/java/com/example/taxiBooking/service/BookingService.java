package com.example.taxiBooking.service;

import com.example.taxiBooking.contract.request.BookingRequest;
import com.example.taxiBooking.contract.response.BookingResponse;
import com.example.taxiBooking.contract.response.TaxiResponse;
import com.example.taxiBooking.exception.BookingNotFoundException;
import com.example.taxiBooking.exception.TaxiNotFoundException;
import com.example.taxiBooking.exception.UserNotFoundException;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.model.Taxi;
import com.example.taxiBooking.model.User;
import com.example.taxiBooking.repository.BookingRepository;
import com.example.taxiBooking.repository.TaxiRepository;
import com.example.taxiBooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TaxiRepository taxiRepository;

    public BookingResponse book(BookingRequest request, Long UserId, Long TaxiId, double distance) {

        Taxi  taxi =taxiRepository
                .findById(TaxiId)
                .orElseThrow(
                        ()-> new TaxiNotFoundException(" not available near pickup location")
                );
        User  user = userRepository
                .findById(UserId)
                .orElseThrow(
                        ()-> new UserNotFoundException("user not found")
                );
        double fare = ((distance)*30.00);
        Booking booking1= Booking.builder()
                .user(user)
                .taxi(taxi)
                .fare(fare)
                .bookingTime(LocalDateTime.now())
                .pickUpLocation(request.getPickUpLocation())
                .dropOffLocation(request.getDropOffLocation())
                .bookingStatus(true)
                .rideStatus(false)
                .build();
        bookingRepository.save(booking1);
        return modelMapper.map(booking1,BookingResponse.class);
    }
    public List<TaxiResponse> availableTaxi(String pickupLocation) {

        List<Taxi> availableTaxis = taxiRepository.findAll().stream()
                .filter(taxi -> pickupLocation.equals(taxi.getCurrentLocation()))
                .collect(Collectors.toList());

        if (availableTaxis.isEmpty()) {
            throw new TaxiNotFoundException("No available taxis found at pickup location: " + pickupLocation);
        }

        return availableTaxis.stream()
                .map(taxi -> modelMapper.map(taxi, TaxiResponse.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    public Booking getById(Long id) {
        return bookingRepository
                .findById(id)
                .orElseThrow(
                        ()-> new BookingNotFoundException("booking not found"));
    }

    public void cancelBooking(Long id) {
        Booking booking = bookingRepository
                .findById(id)
                .orElseThrow(
                        ()->new BookingNotFoundException("booking not found")
                );
        booking.setBookingStatus(false);
        bookingRepository.save(booking);
    }



}
