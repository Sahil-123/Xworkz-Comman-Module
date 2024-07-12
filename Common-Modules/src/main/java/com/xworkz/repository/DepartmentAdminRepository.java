package com.xworkz.repository;

import com.xworkz.dto.DepartmentAdminDTO;
import java.util.List;
import java.util.Optional;

public interface DepartmentAdminRepository {
    Optional<List<DepartmentAdminDTO>> findAll();
    Optional<DepartmentAdminDTO> findById(Long id);
    boolean save(DepartmentAdminDTO departmentAdminDTO);
    boolean update(DepartmentAdminDTO departmentAdminDTO);
    boolean deleteById(Long id);
    Optional<DepartmentAdminDTO> findByEmail(String email);
}
