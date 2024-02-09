package com.example.taxiBooking.controllerTest;

import com.example.taxiBooking.contract.request.LoginRequest;
import com.example.taxiBooking.contract.request.SignUpRequest;
import com.example.taxiBooking.contract.request.UpdateAccountRequest;
import com.example.taxiBooking.contract.response.LoginResponse;
import com.example.taxiBooking.contract.response.SignUpResponse;
import com.example.taxiBooking.contract.response.UpdateAccountResponse;
import com.example.taxiBooking.controller.UserController;
import com.example.taxiBooking.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void testRegister() throws Exception {

        SignUpRequest request = new SignUpRequest("null", "hh@gmail.com", "null");
        SignUpResponse expectedResponse = new SignUpResponse( 1L, "aa","hh@gmail.com");

        when(userService.register(any(SignUpRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/v1/user/registration")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }
    @Test
    void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("hh@gmail.com", "Helloworld");
        LoginResponse expectedResponse =
                new LoginResponse(
                        "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoic2hhcm9rIiwiaWQiOjIsInN1YiI6IjJAZ21haWwuY29tIiwiaWF0IjoxNzA2OTM5Njg3LCJleHAiOjE3MDcwMjYwODd9.FhuhQzgMlXpdCyEJ0hfm8VNbvBYgv6eeZcwhpacfQEg");
        when(userService.userLogin(any(LoginRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
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
