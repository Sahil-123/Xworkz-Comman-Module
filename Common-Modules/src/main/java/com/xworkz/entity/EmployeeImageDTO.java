package com.xworkz.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_images")
@Data
@AllArgsConstructor
@NamedQuery(name = "updateEmployeeImageActive", query = "update EmployeeImageDTO image set image.active=:active where image.employeeId=:employeeId")
@NamedQuery(name = "findImagesByEmployeeID", query = "select image from EmployeeImageDTO image where image.employeeId=:employeeId and image.active=true")
public class EmployeeImageDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_type")
    private String imageType;

    @Column(name = "image_size")
    private int imageSize;

    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "active")
    private boolean active;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public EmployeeImageDTO() {
        System.out.println("EmployeeImageDTO object is created.");
    }
}
