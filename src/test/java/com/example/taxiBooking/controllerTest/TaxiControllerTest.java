package com.example.taxiBooking.controllerTest;

import com.example.taxiBooking.contract.request.TaxiRequest;
import com.example.taxiBooking.contract.response.TaxiResponse;
import com.example.taxiBooking.service.TaxiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
@SpringBootTest
public class TaxiControllerTest {
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
