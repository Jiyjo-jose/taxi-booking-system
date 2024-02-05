package com.example.taxiBooking.controllerTest;

import com.example.taxiBooking.contract.request.BookingRequest;
import com.example.taxiBooking.contract.response.BookingResponse;
import com.example.taxiBooking.contract.response.TaxiResponse;
import com.example.taxiBooking.controller.BookingController;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
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

    @Test
    void testGetById(){
        Booking sampleBooking = new Booking(1L,null,null,null,null,10d,null,true,true);
        when(bookingService.getById(anyLong())).thenReturn(sampleBooking);
        Booking result = bookingService.getById(1L);
        assertEquals(result,sampleBooking);
    }

    @Test
    void testAvailableTaxi(){
        List<TaxiResponse> testResponses = new ArrayList<>();
        when(bookingService.availableTaxi(anyString())).thenReturn(testResponses);
        List<TaxiResponse> result = bookingController.availableTaxi(null);
        assertEquals(testResponses,result);
    }
@Test
public void testCancelBooking() {

    Long bookingId = 1L;
    BookingService bookingService = mock(BookingService.class);
    BookingController bookingController = new BookingController(bookingService);


    ResponseEntity<String> response = bookingController.cancelBooking(bookingId);

    assertEquals(ResponseEntity.ok("booking cancelled"), response);
    verify(bookingService, times(1)).cancelBooking(bookingId);
}


    }
