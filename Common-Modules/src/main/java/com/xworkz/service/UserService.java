package com.xworkz.service;

import com.xworkz.requestDto.RequestSignupDTO;

public interface UserService {
    Boolean validateAndSave(RequestSignupDTO signupDTO);
}
