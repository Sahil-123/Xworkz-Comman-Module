package com.xworkz.service;

import com.xworkz.dto.DTOListPage;
import com.xworkz.dto.DepartmentDTOListPage;
import com.xworkz.entity.DepartmentDTO;
import com.xworkz.requestDto.RequestDepartmentDTO;
import com.xworkz.responseDto.DepartmentNameAndIdResponseDto;
import com.xworkz.responseDto.ResponseDTO;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Optional<List<DepartmentDTO>> getAllDepartments();

    DepartmentDTOListPage<DepartmentDTO> getAllDepartmentsWithPagination(Integer offset, Integer pageSize);

    List<DepartmentNameAndIdResponseDto> getAllDepartmentsNameAndId();

    ResponseDTO checkDepartmentName(String departmentName);

    Boolean validateAndSave(RequestDepartmentDTO requestDepartmentDTO);
}
