package com.example.taxiBooking.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.taxiBooking.contract.request.BookingRequest;
import com.example.taxiBooking.contract.response.BookingResponse;
import com.example.taxiBooking.contract.response.TaxiResponse;
import com.example.taxiBooking.controller.BookingController;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
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

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookingControllerTest {
    @Autowired private MockMvc mockMvc;

    @MockBean private BookingService bookingService;

    @Test
    void testBookTaxi() throws Exception {
        BookingRequest bookingRequest = new BookingRequest("Aluva", "Kakkanad");
        Long taxiId = 1L;
        Long userId = 1L;
        double distance = 80d;
        BookingResponse expectedResponse = new BookingResponse();

        when(bookingService.book(any(BookingRequest.class), anyLong(), anyLong(), anyDouble()))
                .thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/v2/booking")
                                .param("userId", String.valueOf(userId))
                                .param("taxiId", String.valueOf(taxiId))
                                .param("distance", String.valueOf(distance))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(bookingRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void testGetById() throws Exception {
        Long bookingId = 1L;
        Booking booking = new Booking();

        when(bookingService.getById(bookingId)).thenReturn(booking);

        mockMvc.perform(
                        get("/v2/{id}/viewBooking", bookingId)
                                .contentType(MediaType.APPLICATION_JSON))
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

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/v2/searchTaxi")
                                .param("pickupLocation", pickupLocation))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(mockTaxiList)));
        Mockito.verify(bookingService, Mockito.times(1)).availableTaxi(pickupLocation);
    }
}
