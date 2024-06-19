package com.xworkz.repository;

import com.xworkz.dto.ImageDTO;

import java.util.List;
import java.util.Optional;

public interface ImageRepository {
    boolean save(ImageDTO imageDTO);

    Optional<List<ImageDTO>> getImagesByUserId(Long userId);

    boolean updateImageActiveByUserId(List<ImageDTO> imageDTOList);
}
