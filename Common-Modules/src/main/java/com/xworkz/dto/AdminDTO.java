package com.xworkz.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "Admin")
@NamedQuery(name = "findByAdminEmail", query = "Select admin from AdminDTO admin where admin.email=:email")
public class AdminDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "FirstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 50)
    private String lastName;

    @Column(name = "Email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "Mobile", nullable = false, length = 10)
    private String mobile;

    @Column(name = "LoginCount", nullable = false)
    private int loginCount;

    @Column(name = "FailedAttemptsCount", nullable = false)
    private int failedAttemptsCount;

    @Column(name = "FailedAttemptDateTime")
    private LocalDateTime failedAttemptDateTime;

    @Column(name = "CreatedBy", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;

    @Column(name = "UpdatedBy", length = 50)
    private String updatedBy;

    @Column(name = "UpdatedDate")
    private LocalDateTime updatedDate;

    @Column(name = "User_Lock", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean lock;

    public AdminDTO(){
        System.out.println("AdminDTO object is created");
    }
}
