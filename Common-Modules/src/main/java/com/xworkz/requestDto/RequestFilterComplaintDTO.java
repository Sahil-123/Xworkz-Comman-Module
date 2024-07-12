package com.xworkz.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RequestFilterComplaintDTO implements Serializable {

    private Long id;

    private String complaintType;

    private String country;

    private String state;

    private String city;

    private String address;

    private Long deptId;

    private Long empId;

    private String description;

    private LocalDateTime createdDate;

    private String status;

    private Long userId;

    public RequestFilterComplaintDTO(){
        System.out.println("Request Filter Complaint Dto object is created.");
    }
}
