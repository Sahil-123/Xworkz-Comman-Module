package com.xworkz.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "departmentadmin")
public class DepartmentAdminDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fname", nullable = false, length = 30)
    private String fname;

    @Column(name = "lname", nullable = false, length = 30)
    private String lname;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "mobile", nullable = false, unique = true, length = 10)
    private String mobile;

    @Column(name = "departmentId", nullable = false)
    private Long departmentId;

    @Column(name = "loginCount", columnDefinition = "INT DEFAULT 0")
    private int loginCount = 0;

    @Column(name = "failedAttempts", columnDefinition = "INT DEFAULT 0")
    private int failedAttempts;

    @Column(name = "failedAttemptsDateTime")
    private LocalDateTime failedAttemptsDateTime;

    @Column(name = "createdBy", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "createdDate", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;

    @Column(name = "updatedBy", length = 50)
    private String updatedBy;

    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;

    @Column(name = "admin_lock", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean lock = false;

    @Column(name = "ResetPasswordDateTime")
    private LocalDateTime resetPasswordDateTime;

    public DepartmentAdminDTO(){

    }

    public static String[] exportToAdmin() {
        return new String[]{
                "id",
                "fname",
                "lname",
                "email",
                "password",
                "mobile",
                "departmentId",
                "loginCount",
                "failedAttempts",
                "failedAttemptsDateTime",
                "createdBy",
                "createdDate",
                "updatedBy",
                "updatedDate",
                "lock",
                "resetPasswordDateTime"
        };
    }
}
