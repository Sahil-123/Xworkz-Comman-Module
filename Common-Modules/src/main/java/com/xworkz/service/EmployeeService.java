package com.xworkz.service;

import com.xworkz.requestDto.RequestRegisterEmployeeDTO;
import com.xworkz.requestDto.RequestSignupDTO;
import com.xworkz.responseDto.ResponseDTO;
import org.springframework.ui.Model;

public interface EmployeeService {

    Boolean validateAndSaveByDeptAdmin(RequestRegisterEmployeeDTO requestRegisterEmployeeDTO, Model model);

    ResponseDTO checkMobile(String mobile);

    ResponseDTO checkMail(String mail);

}
