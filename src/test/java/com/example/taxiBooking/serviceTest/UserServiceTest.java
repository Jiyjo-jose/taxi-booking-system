package com.example.taxiBooking.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.taxiBooking.contract.request.LoginRequest;
import com.example.taxiBooking.contract.request.SignUpRequest;
import com.example.taxiBooking.contract.request.UpdateAccountRequest;
import com.example.taxiBooking.contract.response.LoginResponse;
import com.example.taxiBooking.contract.response.SignUpResponse;
import com.example.taxiBooking.contract.response.UpdateAccountResponse;
import com.example.taxiBooking.exception.BookingNotFoundException;
import com.example.taxiBooking.exception.EmailAlreadyExistsException;
import com.example.taxiBooking.exception.LowAccountBalanceException;
import com.example.taxiBooking.exception.UserNotFoundException;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.model.User;
import com.example.taxiBooking.repository.BookingRepository;
import com.example.taxiBooking.repository.UserRepository;
import com.example.taxiBooking.security.JwtService;
import com.example.taxiBooking.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    @InjectMocks UserService userService;
    @Mock UserRepository userRepository;
    @Mock BookingRepository bookingRepository;
    @Mock private JwtService jwtService;
    @Mock PasswordEncoder passwordEncoder;
    @Mock ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegister() {
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        SignUpResponse response = SignUpResponse.builder().name(null).id(1L).build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<SignUpResponse>>any()))
                .thenReturn(response);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn(null);
        userService.register(new SignUpRequest(null, null, null));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<SignUpResponse>>any());
        verify(userRepository).save(Mockito.<User>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    @Test
    public void testRegister_ExistingEmail_ExceptionThrown() {

        SignUpRequest request = new SignUpRequest("aa", "j@gmail.com", "password123");

        when(userRepository.existsByEmail("j@gmail.com")).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.register(request));
        verify(userRepository, times(1)).existsByEmail("j@gmail.com");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(modelMapper, never()).map(any(User.class), eq(SignUpResponse.class));
    }

    @Test
    public void testUserLoginSuccess() {
        LoginRequest request = new LoginRequest("test@example.com", "password");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password"));

        when(userRepository.findByEmail(request.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);

        String mockToken = "mockToken";
        when(jwtService.generateToken(user)).thenReturn(mockToken);

        LoginResponse response = userService.userLogin(request);

        assertNotNull(response);
        assertEquals(mockToken, response.getToken());
    }

    @Test
    public void testUserLoginInvalidPassword() {
        LoginRequest request = new LoginRequest("test@example.com", "invalid_password");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password"));

        when(userRepository.findByEmail(request.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.userLogin(request));
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
    void testUpdateAccountWhenUserNotFound() {

        Long userId = 1L;
        UpdateAccountRequest request = new UpdateAccountRequest();
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        UserNotFoundException exception =
                assertThrows(
                        UserNotFoundException.class,
                        () -> userService.updateAccount(userId, request));

        verify(userRepository, times(1)).findById(userId);

        verify(userRepository, never()).save(any());

        assertEquals("user not found", exception.getMessage());
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
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());
        assertThrows(
                BookingNotFoundException.class,
                () -> userService.completeRide(userId, bookingId, updateAccountResponse));
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.completeRide(userId, bookingId, updateAccountResponse);

        verify(bookingRepository).save(booking);

        assertTrue(booking.isRideStatus(true));
    }

    @Test
    public void testLowAccountBalanceException() {
        String expectedMessage = " you don't have enough money for the ride sorry ";
        LowAccountBalanceException exception =
                assertThrows(
                        LowAccountBalanceException.class,
                        () -> {
                            throw new LowAccountBalanceException();
                        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}
