package com.xworkz.requestDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RequestSignupDTO implements Serializable {
    @NotBlank(message = "Please provide valid first name.")
    @Size(min = 1, max = 30, message = "First Name must be between 1 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "First Name must contain only letters")
    private String fname;

    @NotBlank(message = "Please provide valid last name")
    @Size(min = 1, max = 30, message = "Last Name must be between 1 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Last Name must contain only letters")
    private String lname;

    @Email(message = "Please provide valid email")
//    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message="Email must be valid")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp="\\d{10}", message = "Phone must be 10 digits")
    private String mobile;

    @NotNull(message = "You must agree to the terms")
    @Pattern(regexp = "on", message = "You must agree to the terms")
    private String agree;

    public RequestSignupDTO(){
        System.out.println("Sigup DTO's object is created.");
    }
}
