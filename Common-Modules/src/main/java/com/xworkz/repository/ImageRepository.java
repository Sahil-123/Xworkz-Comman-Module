package com.xworkz.repository;

import com.xworkz.dto.ImageDTO;

public interface ImageRepository {
    boolean save(ImageDTO imageDTO);
}
