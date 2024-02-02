package com.example.taxiBooking.serviceTest;

import com.example.taxiBooking.contract.request.BookingRequest;
import com.example.taxiBooking.contract.response.BookingResponse;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.repository.BookingRepository;
import com.example.taxiBooking.repository.TaxiRepository;
import com.example.taxiBooking.repository.UserRepository;
import com.example.taxiBooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BookingServiceTest {

    private  ModelMapper modelMapper;
    private  BookingRepository bookingRepository;
    private  UserRepository userRepository;
    private  TaxiRepository taxiRepository;
    private BookingService bookingService;


    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        userRepository = Mockito.mock(UserRepository.class);
        taxiRepository = Mockito.mock(TaxiRepository.class);
        bookingRepository = Mockito.mock(BookingRepository.class);
        modelMapper=new ModelMapper();
        bookingService=new BookingService(modelMapper,bookingRepository,userRepository,taxiRepository);
    }

    @Test
    void testBook(){
        Booking savedBooking= new Booking();
        BookingResponse expectedResponse= modelMapper.map(savedBooking,BookingResponse.class);
        when(bookingRepository.save(any())).thenReturn(savedBooking);
        BookingResponse actualResponse = new BookingResponse();
        assertEquals(expectedResponse,actualResponse);
    }
}
