package com.xworkz.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestComplaintDTO implements Serializable {
    @NotBlank(message = "Complaint Type is required.")
    @Size(max = 50, message = "Complaint Type must be less than 50 characters.")
    private String complaintType;

    @NotBlank(message = "Country is required.")
    @Size(max = 50, message = "Country must be less than 50 characters.")
    private String country;

    @NotBlank(message = "State is required.")
    @Size(max = 50, message = "State must be less than 50 characters.")
    private String state;

    @NotBlank(message = "City is required.")
    @Size(max = 50, message = "City must be less than 50 characters.")
    private String city;

    @NotBlank(message = "Address is required.")
    @Size(max = 255, message = "Address must be less than 255 characters.")
    private String address;

    @NotBlank(message = "Description is required.")
    @Size(max = 1000, message = "Description must be less than 1000 characters.")
    private String description;
}
