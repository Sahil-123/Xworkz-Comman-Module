package com.xworkz.service;

import com.xworkz.entity.EmployeeImageDTO;
import com.xworkz.entity.EmployeeDTO;
import com.xworkz.repository.EmployeeImageRepository;
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
public class EmployeeImageServiceImpl implements EmployeeImageService {

    @Autowired
    private EmployeeImageRepository employeeImageRepository;

    @Override
    public EmployeeImageDTO uploadImage(MultipartFile file, EmployeeDTO employeeDTO) throws IOException {
        System.out.println("Employee Image upload process is initiated");

        String fileName = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + file.getOriginalFilename();
        Path path = Paths.get("D:\\Xworkz-Comman-Module\\uploadedImages\\" + fileName);
        Files.write(path, file.getBytes());

        EmployeeImageDTO image = new EmployeeImageDTO();
        image.setImageName(fileName);
        image.setImageType(file.getContentType());
        image.setImageSize((int) file.getSize());
        image.setEmployeeId(employeeDTO.getId().intValue());
        image.setActive(true);
        image.setCreatedBy(employeeDTO.getFname() + " " + employeeDTO.getLname());
        image.setCreatedDate(LocalDateTime.now());

        Optional<List<EmployeeImageDTO>> imageDTOList = employeeImageRepository.getImagesByEmployeeId(employeeDTO.getId());

        if (imageDTOList.isPresent()) {
            List<EmployeeImageDTO> imageDTOS = imageDTOList.get();

            List<EmployeeImageDTO> imageDTOS1 = imageDTOS.stream().map(imageDTO -> {
                imageDTO.setActive(false);
                return imageDTO;
            }).collect(Collectors.toList());

            employeeImageRepository.updateImageActiveByEmployeeId(imageDTOS1);
        }

        employeeImageRepository.save(image);

        return image;
    }

    @Override
    public Optional<EmployeeImageDTO> findActiveImageByEmployeeId(Long employeeId) {
        Optional<List<EmployeeImageDTO>> imageDTOList = employeeImageRepository.getImagesByEmployeeId(employeeId);

        if (imageDTOList.isPresent() && !imageDTOList.get().isEmpty()) {
            return Optional.ofNullable(imageDTOList.get().get(0));
        }

        return Optional.empty();
    }
}
