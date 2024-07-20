package com.xworkz.service;

import com.xworkz.entity.EmployeeImageDTO;
import com.xworkz.entity.EmployeeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface EmployeeImageService {

    EmployeeImageDTO uploadImage(MultipartFile file, EmployeeDTO employeeDTO) throws IOException;

    Optional<EmployeeImageDTO> findActiveImageByEmployeeId(Long employeeId);

}
