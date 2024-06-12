package com.xworkz.service;

import com.xworkz.dto.UserDTO;
import com.xworkz.requestDto.RequestResetPasswordDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.requestDto.RequestSignupDTO;

public interface UserService {
    Boolean validateAndSave(RequestSignupDTO signupDTO);

    String signin(RequestSigningDTO requestSigningDTO);

    boolean validateAndResetPassword(RequestResetPasswordDTO requestResetPasswordDTO);
}
