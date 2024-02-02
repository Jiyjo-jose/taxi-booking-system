package com.example.taxiBooking.controllerTest;

import com.example.taxiBooking.contract.request.BookingRequest;
import com.example.taxiBooking.contract.request.TaxiRequest;
import com.example.taxiBooking.contract.response.BookingResponse;
import com.example.taxiBooking.contract.response.TaxiResponse;
import com.example.taxiBooking.controller.BookingController;
import com.example.taxiBooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class BookingControllerTest {

    @InjectMocks
    private BookingController bookingController;
    @Mock
    private BookingService bookingService;
    @BeforeEach

    public  void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testBook(){
        BookingRequest bookingRequest= new BookingRequest(null,null);
        BookingResponse bookingResponse = new BookingResponse(1L,null,null,null,10.00);
        when(bookingService.book(bookingRequest,1L,1L,10.00))
                .thenReturn(bookingResponse);
    }
}
