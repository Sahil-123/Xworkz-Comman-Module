package com.xworkz.requestDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RequestResetPasswordDTO {

    @Email(message = "Please provide valid email")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message="Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "New Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String newPassword;

    @NotBlank(message = "New Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String confirmPassword;

    public RequestResetPasswordDTO(){
        System.out.println("Request Reset Password DTO's object is created.");
    }
}
