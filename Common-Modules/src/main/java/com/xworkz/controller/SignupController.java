package com.xworkz.controller;

import com.xworkz.requestDto.RequestSignupDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class SignupController {

    @PostMapping(value = "/signup")
    public String processSignupForm(@Valid RequestSignupDTO requestSignupDTO, BindingResult bindingResult, Model model) {
        System.out.println("Signup Form processing with dto "+requestSignupDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("dto",requestSignupDTO);
            model.addAttribute("errors",bindingResult.getAllErrors());
            model.addAttribute("errorMessage","Email already exist");
            return "SignUp";
        }


        model.addAttribute("successMessage", "Signup successful!");
        return "SignUp";
    }
}
