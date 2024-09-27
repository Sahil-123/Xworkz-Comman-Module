package com.xworkz.entity;

import com.xworkz.enums.ComplaintStatus;
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
    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "otp", length = 10)
    private String otp;

    @Column(name = "otptime")
    private LocalDateTime otptime;

    @Column(name = "comment")
    private String comment;

    public static String[] exportToAdmin(){
        String[] fieldNames = {
                "id",
                "complaintType",
                "country",
                "state",
                "city",
                "address",
                "deptId",
                "empId",
                "description",
                "createdBy",
                "createdDate",
                "updatedBy",
                "updatedDate",
                "status",
                "userId",
                "otp",
                "otptime",
                "comment"
        };

        return fieldNames;
    }

    public static String[] exportToDepartmentAdmin() {
        String[] fieldNames = {
                "complaintType",
                "country",
                "state",
                "city",
                "address",
                "empId",
                "description",
                "status",
        };

        return fieldNames;
    }

    public static String[] exportToEmployee() {
        String[] fieldNames = {
                "country",
                "state",
                "city",
                "address",
                "description",
                "status"
        };

        return fieldNames;
    }

    public static String[] exportToEmployeeWithResolved() {
        String[] fieldNames = {
                "country",
                "state",
                "city",
                "address",
                "description",
                "status",
                "comment"
        };

        return fieldNames;
    }
}
