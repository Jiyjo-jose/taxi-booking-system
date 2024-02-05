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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    @Test
    void testLogin(){
        LoginRequest request = new LoginRequest(null,null);
        LoginResponse response = new LoginResponse(null);
        when(userService.userLogin(request))
                .thenReturn(response);
    }
    @Test
    void testUpdateAccount() {
        Long userId = 1L;
        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();

        UpdateAccountResponse updatedAccount = new UpdateAccountResponse();
        when(userService.updateAccount(userId, updateAccountRequest)).thenReturn(updatedAccount);
        ResponseEntity<UpdateAccountResponse> responseEntity =
                userController.updateAccount(userId, updateAccountRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedAccount, responseEntity.getBody());

        verify(userService, times(1)).updateAccount(userId,updateAccountRequest );
    }
    @Test
    void testCompleteRide() {
        Long userId = 1L;
        Long bookingId = 1L;
        UpdateAccountResponse updateAccountResponse= new UpdateAccountResponse();
        doNothing().when(userService).completeRide(userId, bookingId,updateAccountResponse);
        ResponseEntity<String> responseEntity =
                userController.completeRide(userId, bookingId,updateAccountResponse);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("ride completed", responseEntity.getBody());
        verify(userService, times(1)).completeRide(userId, bookingId,updateAccountResponse);
    }
}
