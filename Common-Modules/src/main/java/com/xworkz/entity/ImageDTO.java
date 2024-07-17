package com.xworkz.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "image")
@Data
@AllArgsConstructor
@NamedQuery(name = "updateImageActive", query = "update ImageDTO image set image.active=:active where image.userId=:userId")
@NamedQuery(name = "findImagesByUserID", query = "select image from ImageDTO image where image.userId=:userId and image.active=true")
public class ImageDTO implements Serializable {
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

    @Column(name = "user_id")
    private int userId;

    @Column(name = "active")
    private boolean active;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public ImageDTO(){
        System.out.println("Image DTO object is created.");
    }
}
