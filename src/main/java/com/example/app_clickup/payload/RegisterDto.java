package com.example.app_clickup.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotNull(message = "full name not available")
    private String fullName;

    @NotNull(message = "email not available")
    private String email;

    @NotNull(message = "password not available")
    private String password;

}
