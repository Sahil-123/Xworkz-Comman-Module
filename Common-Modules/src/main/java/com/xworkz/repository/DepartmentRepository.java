package com.xworkz.repository;

import com.xworkz.dto.DTOListPage;
import com.xworkz.dto.DepartmentDTOListPage;
import com.xworkz.entity.DepartmentDTO;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    Optional<List<DepartmentDTO>> findAll();

    DepartmentDTOListPage<DepartmentDTO> findAll(Integer offset, Integer pageSize);


    Optional<DepartmentDTO> findById(long id);

    public boolean checkDepartmentName(String departmentName);

    Boolean save(DepartmentDTO departmentDTO);
}
