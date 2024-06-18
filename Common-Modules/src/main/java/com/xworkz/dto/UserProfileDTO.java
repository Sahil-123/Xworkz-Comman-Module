package com.xworkz.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserProfileDTO {
    @NotBlank(message = "First name is mandatory")
    @Size(max = 50, message = "First name should not exceed 50 characters")
    private String fname;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    private String lname;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
    private String mobile;

    private MultipartFile profilePicture;

    public UserProfileDTO(){
        System.out.println("UserProfileDTO object is created.");
    }
}
