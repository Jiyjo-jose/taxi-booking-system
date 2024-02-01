package com.example.taxiBooking.contract.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "name cannot be null")
    private String name;
    @NotBlank(message = "email cannot be null")
    @Email
    private String email;

    @NotBlank(message = "password cannot be null")
    private String password;


}
