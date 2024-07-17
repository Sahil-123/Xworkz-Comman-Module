package com.xworkz.service;

import com.xworkz.entity.ImageDTO;
import com.xworkz.entity.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageService {

    ImageDTO uploadImage(MultipartFile file, UserDTO userDTO) throws IOException;

    Optional<ImageDTO> findActiveImageByUserId(Long userId);

}
