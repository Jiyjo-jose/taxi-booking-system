package com.example.taxiBooking.controller;

import com.example.taxiBooking.contract.request.SignUpRequest;
import com.example.taxiBooking.contract.request.UpdateAccountRequest;
import com.example.taxiBooking.contract.response.SignUpResponse;
import com.example.taxiBooking.contract.response.UpdateAccountResponse;
import com.example.taxiBooking.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<SignUpResponse> register(@Valid @RequestBody SignUpRequest request){
        return ResponseEntity.ok(userService.register(request));
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@Valid@RequestBody LoginRequest request){
//        return ResponseEntity.ok(userService.authenticate(request));
//    }

    @PutMapping("/{id}/updateAccountBalance")
    public ResponseEntity<UpdateAccountResponse>updateAccount(@PathVariable Long id, @RequestBody UpdateAccountRequest request){
        UpdateAccountResponse response= userService.updateAccount(id,request);
        return ResponseEntity.ok(response);
    }
        @PutMapping("/rideCompleted")
    public ResponseEntity<String> completeRide(@RequestParam Long userId, @RequestParam Long bookingId, @RequestBody UpdateAccountResponse response){
        userService.completeRide(userId,bookingId, response);
        return ResponseEntity.ok("ride completed");
    }
}
