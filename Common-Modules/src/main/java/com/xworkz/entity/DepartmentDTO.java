package com.xworkz.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Department")
public class DepartmentDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name", nullable = false, length = 100)
    private String departmentName;

    @Column(name = "area", length = 100)
    private String area;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "number_of_employees", columnDefinition = "INT DEFAULT 0")
    private Integer numberOfEmployees = 0;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public static String[] exportToAdmin() {
        return new String[]{
                "id",
                "departmentName",
                "area",
                "address",
                "numberOfEmployees",
                "createdBy",
                "createdDate",
                "updatedBy",
                "updatedDate"
        };
    }
}
