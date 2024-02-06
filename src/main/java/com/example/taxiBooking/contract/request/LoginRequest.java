package com.example.taxiBooking.contract.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "email cannot be null")
    @Email
    private String email;

    @NotBlank(message = "password cannot be null")
    private String password;
}
