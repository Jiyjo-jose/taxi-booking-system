package com.example.taxiBooking.serviceTest;

import com.example.taxiBooking.contract.request.SignUpRequest;
import com.example.taxiBooking.contract.request.UpdateAccountRequest;
import com.example.taxiBooking.contract.response.SignUpResponse;
import com.example.taxiBooking.contract.response.UpdateAccountResponse;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.model.User;
import com.example.taxiBooking.repository.BookingRepository;
import com.example.taxiBooking.repository.UserRepository;
import com.example.taxiBooking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private ModelMapper modelMapper;
    private UserService userService;
    private UserRepository userRepository;
    private BookingRepository bookingRepository;


    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        userRepository = Mockito.mock(UserRepository.class);
        bookingRepository = Mockito.mock(BookingRepository.class);
        modelMapper=new ModelMapper();
        userService=new UserService(userRepository,modelMapper,bookingRepository);
    }

    @Test
    void testRegister(){
        SignUpRequest signUpRequest = new SignUpRequest(null,null,null);
        User savedUser= new User(null,null,null,null,new ArrayList<>(),10d);
        SignUpResponse expectedResponse= modelMapper.map(savedUser,SignUpResponse.class);
        when(userRepository.save(any())).thenReturn(savedUser);
        SignUpResponse actualResponse=userService.register(signUpRequest);
        assertEquals(expectedResponse,actualResponse);
    }
    @Test
    void testUpdateAccount() {

        User sampleUser = new User();
        UpdateAccountRequest request = new UpdateAccountRequest();
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(sampleUser));
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);
        UpdateAccountResponse updatedResponse = userService.updateAccount(1L, request);
        assertEquals(updatedResponse, userService.updateAccount(1L, request));
    }
    @Test
    public void testCompleteRide() {
        Long userId = 1L;
        Long bookingId = 1L;
        UpdateAccountResponse updateAccountResponse = new UpdateAccountResponse();
        Booking booking = new Booking();
        booking.setRideStatus(false);

        User user = new User();
        booking.setId(bookingId);
        booking.setRideStatus(true);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.completeRide(userId, bookingId,updateAccountResponse);

        verify(bookingRepository).save(booking);

        assertTrue(booking.isRideStatus(true));
    }


}
