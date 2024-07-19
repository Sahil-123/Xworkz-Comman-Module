package com.xworkz.repository;

import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.DepartmentAdminDTO;
import java.util.List;
import java.util.Optional;

public interface DepartmentAdminRepository {
    Optional<List<DepartmentAdminDTO>> findAll();

    DTOListPage<DepartmentAdminDTO> findAllByPagination(Integer offset, Integer pageSize);

    Optional<DepartmentAdminDTO> findById(Long id);
    boolean save(DepartmentAdminDTO departmentAdminDTO);
    boolean update(DepartmentAdminDTO departmentAdminDTO);
    boolean deleteById(Long id);
    Optional<DepartmentAdminDTO> findByEmail(String email);

    boolean checkMobile(String mobile);

    boolean checkEmail(String mail);
}
