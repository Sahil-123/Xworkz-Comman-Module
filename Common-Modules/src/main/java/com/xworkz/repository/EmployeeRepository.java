package com.xworkz.repository;

import com.xworkz.entity.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    Optional<List<EmployeeDTO>> findAll();
    Optional<EmployeeDTO> findById(Long id);
    Boolean save(EmployeeDTO employeeDTO);
    Boolean update(EmployeeDTO employeeDTO);
    Boolean deleteById(Long id);
    Optional<EmployeeDTO> findByEmail(String email);

    Optional<List<EmployeeDTO>> findByEmployeeMobile(String mobile);

    boolean checkMobile(String mobile);

    boolean checkEmail(String email);

    Optional<List<EmployeeDTO>> searchAllEmployees(EmployeeDTO employeeDTO);
}
