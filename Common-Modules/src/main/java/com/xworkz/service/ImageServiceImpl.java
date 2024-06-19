package com.xworkz.service;

import com.xworkz.dto.ImageDTO;
import com.xworkz.dto.UserDTO;
import com.xworkz.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public boolean uploadImage(MultipartFile file, UserDTO userDTO) throws IOException {
        System.out.println("Image upload process is initiated");

        String fileName = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)+file.getOriginalFilename();
        Path path = Paths.get("D:\\Xworkz-Comman-Module\\uploadedImages\\"+fileName);
        Files.write(path,file.getBytes());

        ImageDTO image = new ImageDTO();
        image.setImageName(fileName);
        image.setImageType(file.getContentType());
        image.setImageSize((int) file.getSize());
        image.setUserId(userDTO.getId().intValue());
        image.setActive(true);

        return imageRepository.save(image);
    }
}
