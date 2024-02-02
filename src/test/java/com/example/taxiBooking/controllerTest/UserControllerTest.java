package com.example.taxiBooking.controllerTest;

import com.example.taxiBooking.contract.request.SignUpRequest;
import com.example.taxiBooking.contract.response.SignUpResponse;
import com.example.taxiBooking.controller.UserController;
import com.example.taxiBooking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    @BeforeEach

    public  void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister(){
        SignUpRequest signUpRequest= new SignUpRequest(null,null,null);
        SignUpResponse signUpResponse = new SignUpResponse(1L,null,null,null);
        when(userService.register(signUpRequest))
                .thenReturn(signUpResponse);
    }
}
