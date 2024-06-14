package com.xworkz.service;

import com.xworkz.requestDto.RequestForgotPasswordDTO;
import com.xworkz.requestDto.RequestResetPasswordDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import org.springframework.ui.Model;

public interface AdminService {

    String signin(RequestSigningDTO requestSigningDTO, Model model);

    String validateAndSetForgotPassword(RequestForgotPasswordDTO requestForgotPasswordDTO,Model model);

    boolean validateAndResetPassword(RequestResetPasswordDTO requestResetPasswordDTO);
}
