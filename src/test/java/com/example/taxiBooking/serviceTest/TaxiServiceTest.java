package com.example.taxiBooking.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.taxiBooking.contract.request.TaxiRequest;
import com.example.taxiBooking.contract.response.TaxiResponse;
import com.example.taxiBooking.model.Taxi;
import com.example.taxiBooking.repository.TaxiRepository;
import com.example.taxiBooking.service.TaxiService;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class TaxiServiceTest {

    private TaxiRepository taxiRepository;
    private TaxiService taxiService;
    private ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        taxiRepository = Mockito.mock(TaxiRepository.class);
        modelMapper = new ModelMapper();
        taxiService = new TaxiService(modelMapper, taxiRepository);
    }

    @Test
    void testAddTaxi() {
        TaxiRequest taxiRequest = new TaxiRequest(null, null, null);
        Taxi savedTaxi = new Taxi(null, null, null, null, new ArrayList<>());
        TaxiResponse expectedResponse = modelMapper.map(savedTaxi, TaxiResponse.class);
        when(taxiRepository.save(any())).thenReturn(savedTaxi);
        TaxiResponse actualResponse = taxiService.addTaxi(taxiRequest);
        assertEquals(expectedResponse, actualResponse);
    }
}
