package com.xworkz.service;

import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.ComplaintDTO;
import com.xworkz.entity.DepartmentAdminDTO;
import com.xworkz.requestDto.RequestDepartmentAdminDTO;
import com.xworkz.requestDto.RequestFilterComplaintDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.responseDto.ResponseDTO;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

public interface DepartmentAdminService {

    String signin(RequestSigningDTO requestSigningDTO, Model model);

    Boolean createEmployee();

    Boolean assignEmployee();

    Optional<List<ComplaintDTO>> viewComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO, Model model);

    Boolean validateAndSave(RequestDepartmentAdminDTO requestDepartmentAdminDTO, Model model);

    ResponseDTO checkMobile(String mobile);

    ResponseDTO checkMail(String mail);

    DTOListPage<DepartmentAdminDTO> findAllDepartmentAdmin(Integer offset, Integer pageSize);
}
