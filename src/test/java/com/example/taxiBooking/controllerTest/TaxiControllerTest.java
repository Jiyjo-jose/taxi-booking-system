package com.example.taxiBooking.controllerTest;

import com.example.taxiBooking.contract.request.SignUpRequest;
import com.example.taxiBooking.contract.request.TaxiRequest;
import com.example.taxiBooking.contract.response.SignUpResponse;
import com.example.taxiBooking.contract.response.TaxiResponse;
import com.example.taxiBooking.controller.TaxiController;
import com.example.taxiBooking.controller.UserController;
import com.example.taxiBooking.service.TaxiService;
import com.example.taxiBooking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class TaxiControllerTest {
    @InjectMocks
    private TaxiController taxiController;
    @Mock
    private TaxiService taxiService;
    @BeforeEach

    public  void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testRegister(){
        TaxiRequest taxiRequest= new TaxiRequest(null,null,null);
        TaxiResponse taxiResponse = new TaxiResponse(1L,null,null,null);
        when(taxiService.addTaxi(taxiRequest))
                .thenReturn(taxiResponse);
    }
}
