package com.xworkz.repository;

import com.xworkz.entity.EmployeeImageDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeImageRepository {
    boolean save(EmployeeImageDTO employeeImageDTO);

    Optional<List<EmployeeImageDTO>> getImagesByEmployeeId(Long employeeId);

    boolean updateImageActiveByEmployeeId(List<EmployeeImageDTO> employeeImageDTOList);
}
