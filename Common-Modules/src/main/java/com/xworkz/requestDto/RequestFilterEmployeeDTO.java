package com.xworkz.requestDto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestFilterEmployeeDTO implements Serializable {
    private Long id;
    private String fname;
    private String lname;
    private String email;
    private String password;
    private String mobile;
    private Long departmentId;
    private Integer loginCount; // Changed from int to Integer
    private Integer failedAttempts; // Changed from int to Integer
    private LocalDateTime failedAttemptsDateTime;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
    private Boolean lock; // Changed from boolean to Boolean
}
