package com.xworkz.service;

import com.xworkz.entity.ComplaintDTO;
import com.xworkz.requestDto.RequestFilterComplaintDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

public interface DepartmentAdminService {

    String signin(RequestSigningDTO requestSigningDTO, Model model);

    Boolean createEmployee();

    Boolean assignEmployee();

    Optional<List<ComplaintDTO>> viewComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO, Model model);

}
