package com.example.taxiBooking.service;

import com.example.taxiBooking.contract.request.LoginRequest;
import com.example.taxiBooking.contract.request.SignUpRequest;
import com.example.taxiBooking.contract.request.UpdateAccountRequest;
import com.example.taxiBooking.contract.response.LoginResponse;
import com.example.taxiBooking.contract.response.SignUpResponse;
import com.example.taxiBooking.contract.response.UpdateAccountResponse;
import com.example.taxiBooking.model.User;
import com.example.taxiBooking.repository.UserRepository;
import com.example.taxiBooking.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
    public SignUpResponse register(SignUpRequest request) {
        User user= User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        user = userRepository.save(user);
        return modelMapper.map(user,SignUpResponse.class);
    }

    public UpdateAccountResponse updateAccount(Long id, UpdateAccountRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException()
        );
        modelMapper.map(request,user);
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser,UpdateAccountResponse.class);
    }


//    public LoginResponse authenticate(LoginRequest request) {
//        AuthenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
//        User user = UserRepository.findByEmail(request.getEmail()).orElseThrow();
//        String jwtToken= jwtService.generateToken(user);
//        return LoginResponse.builder().token(jwtToken).build();
//    }
}
