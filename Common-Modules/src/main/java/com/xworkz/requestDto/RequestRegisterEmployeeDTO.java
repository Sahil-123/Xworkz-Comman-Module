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
public class RequestRegisterEmployeeDTO implements Serializable {
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


//    public RequestSignupDTO(){
//        System.out.println("Sigup DTO's object is created.");
//    }
}
