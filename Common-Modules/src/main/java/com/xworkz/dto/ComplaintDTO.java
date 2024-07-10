package com.xworkz.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Complaint")
public class ComplaintDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "complaint_type", nullable = false, length = 50)
    private String complaintType;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "deptId")
    private Long deptId;

    @Column(name = "empId")
    private Long empId;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "status")
    private String status;

    @Column(name = "userId")
    private Long userId;

//    public ComplaintDTO() {
//        System.out.println("Complaint DTO object is created.");
//    }

}
