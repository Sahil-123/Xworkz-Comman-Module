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

    @PostMapping(value = "/signup")
    public String processSignupForm(@Valid RequestSignupDTO requestSignupDTO, BindingResult bindingResult, Model model) {
        System.out.println("Signup Form processing with dto "+requestSignupDTO);

        try{
            if (bindingResult.hasErrors()) {
                model.addAttribute("dto",requestSignupDTO);
                model.addAttribute("errors",bindingResult.getAllErrors());
                model.addAttribute("errorMessage","Email already exist");
                return "SignUp";
            }

            Boolean result = userService.validateAndSave(requestSignupDTO);
            model.addAttribute("successMessage", " Signup successful! Please check your email. We have sent you a password to log in.");
        }catch (InfoException e){
            System.out.println(e.getMessage());
            model.addAttribute("infoError",e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }

        return "SignUp";
    }
}
