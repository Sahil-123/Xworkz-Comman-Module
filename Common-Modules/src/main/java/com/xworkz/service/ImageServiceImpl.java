package com.xworkz.service;

import com.xworkz.entity.ImageDTO;
import com.xworkz.entity.UserDTO;
import com.xworkz.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ImageDTO uploadImage(MultipartFile file, UserDTO userDTO) throws IOException {
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
        image.setCreatedBy(userDTO.getFname()+" "+userDTO.getLname());
        image.setCreatedDate(LocalDateTime.now());

        Optional<List<ImageDTO>> imageDTOList = imageRepository.getImagesByUserId(userDTO.getId());

        if(imageDTOList.isPresent()){
            List<ImageDTO> imageDTOS = imageDTOList.get();

            List<ImageDTO> imageDTOS1 = imageDTOS.stream().map(imageDTO -> {
                imageDTO.setActive(false);
                return imageDTO;
            }).collect(Collectors.toList());

            imageRepository.updateImageActiveByUserId(imageDTOS1);
        }

        imageRepository.save(image);

        return image;
    }

    @Override
    public Optional<ImageDTO> findActiveImageByUserId(Long userId) {
        Optional<List<ImageDTO>> imageDTOList = imageRepository.getImagesByUserId(userId);

        if(imageDTOList.isPresent() && !imageDTOList.get().isEmpty()){
            return Optional.ofNullable(imageDTOList.get().get(0));
        }

        return Optional.empty();
    }
}
