package com.xworkz.service;

import com.xworkz.dto.DepartmentDTO;
import com.xworkz.responseDto.DepartmentNameAndIdResponseDto;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Optional<List<DepartmentDTO>> getAllDepartments();

    List<DepartmentNameAndIdResponseDto> getAllDepartmentsNameAndId();

}
