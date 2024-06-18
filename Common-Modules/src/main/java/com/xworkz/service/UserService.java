package com.xworkz.service;

import com.xworkz.dto.UserDTO;
import com.xworkz.dto.UserProfileDTO;
import com.xworkz.requestDto.RequestForgotPasswordDTO;
import com.xworkz.requestDto.RequestResetPasswordDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.requestDto.RequestSignupDTO;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Boolean validateAndSave(RequestSignupDTO signupDTO);

    String signin(RequestSigningDTO requestSigningDTO, Model model);

    boolean validateAndResetPassword(RequestResetPasswordDTO requestResetPasswordDTO);

    String validateAndSetForgotPassword(RequestForgotPasswordDTO requestForgotPasswordDTO, Model model);

    Optional<List<UserDTO>> getAllUser();

    boolean editProfile(UserProfileDTO userProfileDTO,Model model);


}
