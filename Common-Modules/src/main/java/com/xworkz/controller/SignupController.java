package com.xworkz.controller;

import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.RequestSignupDTO;
import com.xworkz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class SignupController {

    @Autowired
    private UserService userService;


}
