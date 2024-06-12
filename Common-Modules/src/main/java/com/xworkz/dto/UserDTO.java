package com.xworkz.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
@NamedQuery(name = "findByUserEmail", query = "Select user from UserDTO user where user.email=:email")
@NamedQuery(name = "findByUserMobile", query = "Select user from UserDTO user where user.mobile=:mobile")
public class UserDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fname", nullable = false, length = 30)
    private String fname;

    @Column(name = "lname", nullable = false, length = 30)
    private String lname;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "mobile", nullable = false, unique = true, length = 10)
    private String mobile;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "agree", nullable = false, length = 3)
    private String agree;

    @Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "login_count", columnDefinition = "INT DEFAULT 0")
    private int loginCount;

    public UserDTO(){
        System.out.println("User entity object is created.");
    }
}
