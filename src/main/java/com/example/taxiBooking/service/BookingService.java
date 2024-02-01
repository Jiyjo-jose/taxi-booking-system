package com.example.taxiBooking.service;

import com.example.taxiBooking.contract.request.BookingRequest;
import com.example.taxiBooking.contract.response.BookingResponse;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;

    public BookingResponse book(BookingRequest request) {

        Booking booking = modelMapper.map(request,Booking.class);
        Booking booking1=bookingRepository.save(booking);
        return modelMapper.map(booking1,BookingResponse.class);
    }

}
