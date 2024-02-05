package com.example.taxiBooking.controllerTest;

import com.example.taxiBooking.contract.request.LoginRequest;
import com.example.taxiBooking.contract.request.SignUpRequest;
import com.example.taxiBooking.contract.request.UpdateAccountRequest;
import com.example.taxiBooking.contract.response.LoginResponse;
import com.example.taxiBooking.contract.response.SignUpResponse;
import com.example.taxiBooking.contract.response.UpdateAccountResponse;
import com.example.taxiBooking.controller.UserController;
import com.example.taxiBooking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

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
    @Test
    void testLogin(){
        LoginRequest request = new LoginRequest(null,null);
        LoginResponse response = new LoginResponse(null);
        when(userService.userLogin(request))
                .thenReturn(response);
    }

    @Test
    public void testUpdateAccount() {

        Long userId = 1L;
        UpdateAccountRequest request= new UpdateAccountRequest();
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);

        ResponseEntity<UpdateAccountResponse> responseEntity =
                userController.updateAccount(userId,request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService, times(1)).updateAccount(userId,request);
    }



    @Test
    public void testCompleteRide() {

        Long bookingId = 1L;
        Long userId = 1L;
        UpdateAccountResponse updateAccountResponse= new UpdateAccountResponse(2d);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);

        ResponseEntity<String> responseEntity =
                userController.completeRide(userId, bookingId,updateAccountResponse);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("ride completed", responseEntity.getBody());
        verify(userService, times(1)).completeRide(userId,bookingId,updateAccountResponse);
    }

}
