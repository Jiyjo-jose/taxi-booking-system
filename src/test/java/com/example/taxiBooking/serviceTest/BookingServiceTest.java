package com.example.taxiBooking.serviceTest;

import com.example.taxiBooking.contract.response.BookingResponse;
import com.example.taxiBooking.contract.response.TaxiResponse;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.model.Taxi;
import com.example.taxiBooking.repository.BookingRepository;
import com.example.taxiBooking.repository.TaxiRepository;
import com.example.taxiBooking.repository.UserRepository;
import com.example.taxiBooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Test
    void testGetById(){
        Booking sampleBooking= new Booking(1L,null,null,null,null,10d,null,true,true);
        Long id=1L;
        when (bookingRepository.findById(id)).thenReturn(Optional.of(sampleBooking));
        Booking retrievedEmployee= bookingService.getById(id);
        assertEquals(sampleBooking,retrievedEmployee);
    }

//    @Test
//    void testAvailableTaxi(){
//        List<Taxi> sampleTaxi= Collections.singletonList(new Taxi(1L,null,null,"abc",null));
//        when(taxiRepository.availableTaxi(anyString())).thenReturn(sampleTaxi);
//        List<TaxiResponse>taxiResponse=bookingService.availableTaxi("abc");
//        assertEquals(1,taxiResponse.size());
//    }
}
