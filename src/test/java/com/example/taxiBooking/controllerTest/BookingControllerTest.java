package com.example.taxiBooking.controllerTest;

import com.example.taxiBooking.contract.request.BookingRequest;
import com.example.taxiBooking.contract.response.BookingResponse;
import com.example.taxiBooking.contract.response.TaxiResponse;
import com.example.taxiBooking.controller.BookingController;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.repository.TaxiRepository;
import com.example.taxiBooking.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Test
    void testBook() {
        BookingRequest bookingRequest = new BookingRequest("asda", "dasd");
        BookingResponse bookingResponse =
                new BookingResponse(1L, "ADS", "da",  LocalDateTime.now(),10d);
        when(bookingService.book(bookingRequest, 1L, 1L, 10d)).thenReturn(bookingResponse);
    }


    @Test
    void testGetById() throws Exception {
        Long bookingId = 1L;
        Booking booking =
                new Booking();

        when(bookingService.getById(bookingId)).thenReturn(booking);

        mockMvc.perform(get("/v2/{id}/viewBooking", bookingId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(booking)));
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
    @Test
    void testAvailableTaxi() throws Exception {

        String pickupLocation = "TestLocation";
        List<TaxiResponse> mockTaxiList = Arrays.asList(new TaxiResponse());

        Mockito.when(bookingService.availableTaxi(pickupLocation)).thenReturn(mockTaxiList);

        mockMvc.perform(MockMvcRequestBuilders.get("/v2/searchTaxi")
                        .param("pickupLocation", pickupLocation))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(content().json(new ObjectMapper().writeValueAsString(mockTaxiList)));
        Mockito.verify(bookingService, Mockito.times(1)).availableTaxi(pickupLocation);
    }


    }
