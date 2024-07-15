package com.xworkz.service;

import com.xworkz.dto.EmployeeDTO;
import com.xworkz.requestDto.*;
import com.xworkz.responseDto.EmployeeNameAndIdResponseDto;
import com.xworkz.responseDto.ResponseDTO;
import com.xworkz.responseDto.ResponseOTPDto;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Boolean validateAndSaveByDeptAdmin(RequestRegisterEmployeeDTO requestRegisterEmployeeDTO, Model model);

    ResponseDTO checkMobile(String mobile);

    ResponseDTO checkMail(String mail);

    Optional<List<EmployeeDTO>> searchEmployees(RequestFilterEmployeeDTO requestFilterEmployeeDTO,Model model);

    public List<EmployeeNameAndIdResponseDto> searchEmployeesRestService( Model model);

    String signin(RequestSigningDTO requestSigningDTO, Model model);

    boolean validateAndResetPassword(RequestResetPasswordDTO requestResetPasswordDTO);

    String validateAndSetForgotPassword(RequestForgotPasswordDTO requestForgotPasswordDTO,Model model);

    ResponseOTPDto generateOTP(EmployeeDTO employeeDTO, Long complaintId);
}
