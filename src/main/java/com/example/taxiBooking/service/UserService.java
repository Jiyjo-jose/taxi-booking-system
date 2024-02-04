package com.example.taxiBooking.service;

import com.example.taxiBooking.contract.request.SignUpRequest;
import com.example.taxiBooking.contract.request.UpdateAccountRequest;
import com.example.taxiBooking.contract.response.SignUpResponse;
import com.example.taxiBooking.contract.response.UpdateAccountResponse;
//import com.example.taxiBooking.exception.BookingNotFoundException;
//import com.example.taxiBooking.exception.InvalidLoginException;
//import com.example.taxiBooking.exception.UserNotFoundException;
import com.example.taxiBooking.exception.BookingNotFoundException;
import com.example.taxiBooking.exception.InvalidLoginException;
import com.example.taxiBooking.exception.LowAccountBalanceException;
import com.example.taxiBooking.exception.UserNotFoundException;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.model.User;
import com.example.taxiBooking.repository.BookingRepository;
import com.example.taxiBooking.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;
//    private final JwtService jwtService;
//    private final PasswordEncoder passwordEncoder;

//   private final AuthenticationManager authenticationManager;
    public SignUpResponse register(SignUpRequest request) {
        User user= User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        userRepository.save(user);
        return modelMapper.map(user,SignUpResponse.class);
    }
//    public LoginResponse userLogin(LoginRequest request) {
//        User user = userRepository.findByEmail(request.getEmail());
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new UserNotFoundException("Login");
//        }
//        String jwtToken = jwtService.generateToken(user);
//        return LoginResponse.builder().token(jwtToken).build();
//    }

    //    public AuthResponse login(@Valid LoginRequest request) throws Exception {
//        String email = request.getEmail();
//        String password = request.getPassword();
//        if (!userRepository.existsByEmail(email)) {
//            throw new EntityNotFoundException("Invalid login");
//        }
//        User user = userRepository.findByEmail(request.getEmail());
//        if (passwordEncoder.matches(password, user.getPassword())) {
//            return jwtService.generateToken(user);
//        }
//        throw new InvalidLoginException();
//    }
    public UpdateAccountResponse updateAccount(Long id, UpdateAccountRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new UserNotFoundException("user not found")
        );
        modelMapper.map(request,user);
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser,UpdateAccountResponse.class);
    }
    public void completeRide(Long userId, Long bookingId, UpdateAccountResponse response) {
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(
                        ()->new BookingNotFoundException("booking not found")
                );
        booking.setRideStatus(true);
        bookingRepository.save(booking);
        if(booking.isRideStatus(true)){
            bookingRepository.findById(bookingId)
                    .orElseThrow(()-> new BookingNotFoundException("booking not found"));
            if(response.getAccountBalance()<booking.getFare()){
                throw new LowAccountBalanceException();
            }
            double accountBalance = response.getAccountBalance()-booking.getFare();
            User User = userRepository
                    .findById(userId)
                    .orElseThrow(
                            () -> new UserNotFoundException("user not found")
                    );
            User.setAccountBalance(accountBalance);
            userRepository.save(User);
        }
    }

//    public LoginResponse authenticate(LoginRequest request) {
//        AuthenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
//        User user = UserRepository.findByEmail(request.getEmail()).orElseThrow();
//        String jwtToken= jwtService.generateToken(user);
//        return LoginResponse.builder().token(jwtToken).build();
//    }
}
