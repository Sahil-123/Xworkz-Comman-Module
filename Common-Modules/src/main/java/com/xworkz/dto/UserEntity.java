package com.xworkz.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@ToString
@Entity
@Table(name = "user") // Change table name to "user"
public class UserEntity {

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
    private Date createdDate;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "login_count", columnDefinition = "INT DEFAULT 0")
    private int loginCount;

    public UserEntity(){
        System.out.println("User entity object is created.");
    }
}
