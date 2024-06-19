package com.xworkz.service;

import com.xworkz.dto.ImageDTO;
import com.xworkz.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

public interface ImageService {

    ImageDTO uploadImage(MultipartFile file, UserDTO userDTO) throws IOException;

    Optional<ImageDTO> findActiveImageByUserId(Long userId);

}
