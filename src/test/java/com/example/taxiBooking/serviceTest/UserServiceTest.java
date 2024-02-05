package com.example.taxiBooking.serviceTest;

import com.example.taxiBooking.contract.request.LoginRequest;
import com.example.taxiBooking.contract.request.SignUpRequest;
import com.example.taxiBooking.contract.request.UpdateAccountRequest;
import com.example.taxiBooking.contract.response.SignUpResponse;
import com.example.taxiBooking.contract.response.UpdateAccountResponse;
import com.example.taxiBooking.exception.UserNotFoundException;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.model.User;
import com.example.taxiBooking.repository.BookingRepository;
import com.example.taxiBooking.repository.UserRepository;
import com.example.taxiBooking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    BookingRepository bookingRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    ModelMapper modelMapper;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegister(){
        when (userRepository.save(Mockito.<User>any())).thenReturn(new User());
        SignUpResponse response = SignUpResponse.builder().name(null).id(1L).build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<SignUpResponse>>any()))
                .thenReturn(response);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn(null);
        userService.register(new SignUpRequest(null,null,null));
        verify(modelMapper).map(Mockito.<Object>any(),Mockito.<Class<SignUpResponse>>any());
        verify(userRepository).save(Mockito.<User>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());

    }
    @Test
    void testUserLogin(){
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(new User());
        when(passwordEncoder.matches(Mockito.<CharSequence>any(),Mockito.<String>any())).thenReturn(false);
        assertThrows(
                UserNotFoundException.class,
                ()->userService.userLogin(new LoginRequest())
        );
        verify(userRepository).findByEmail(Mockito.<String>any());
        verify(passwordEncoder).matches(Mockito.<CharSequence>any(),Mockito.<String>any());
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
