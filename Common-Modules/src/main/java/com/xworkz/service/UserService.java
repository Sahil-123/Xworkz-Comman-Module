package com.xworkz.service;

import com.xworkz.dto.DTOListPage;
import com.xworkz.responseDto.ResponseDTO;
import com.xworkz.entity.UserDTO;
import com.xworkz.requestDto.UserProfileDTO;
import com.xworkz.requestDto.RequestForgotPasswordDTO;
import com.xworkz.requestDto.RequestResetPasswordDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.requestDto.RequestSignupDTO;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Boolean validateAndSave(RequestSignupDTO signupDTO);

    String signin(RequestSigningDTO requestSigningDTO, Model model);

    boolean validateAndResetPassword(RequestResetPasswordDTO requestResetPasswordDTO);

    String validateAndSetForgotPassword(RequestForgotPasswordDTO requestForgotPasswordDTO, Model model);

    DTOListPage<UserDTO> getAllUser(Integer offset,Integer pageSize);

    boolean editProfile(UserProfileDTO userProfileDTO,Model model) throws IOException;

    ResponseDTO checkMobile(String mobile);

    ResponseDTO checkMail(String mail);

}
