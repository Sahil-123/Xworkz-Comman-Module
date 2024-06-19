package com.xworkz.dto;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@Data
@AllArgsConstructor
public class ImageDTO {
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

    public ImageDTO(){
        System.out.println("Image DTO object is created.");
    }
}
