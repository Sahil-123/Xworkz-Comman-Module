package com.xworkz.service;

import com.xworkz.dto.UserDTO;
import com.xworkz.requestDto.RequestResetPasswordDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.requestDto.RequestSignupDTO;
import org.springframework.ui.Model;

public interface UserService {
    Boolean validateAndSave(RequestSignupDTO signupDTO);

    String signin(RequestSigningDTO requestSigningDTO, Model model);

    boolean validateAndResetPassword(RequestResetPasswordDTO requestResetPasswordDTO);
}
