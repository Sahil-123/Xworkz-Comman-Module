package com.xworkz.repository;

import com.xworkz.entity.DepartmentDTO;
import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    Optional<List<DepartmentDTO>> findAll();

    Optional<DepartmentDTO> findById(long id);
}
