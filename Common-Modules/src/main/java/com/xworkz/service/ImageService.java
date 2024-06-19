package com.xworkz.service;

import com.xworkz.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    boolean uploadImage(MultipartFile file, UserDTO userDTO) throws IOException;

}
