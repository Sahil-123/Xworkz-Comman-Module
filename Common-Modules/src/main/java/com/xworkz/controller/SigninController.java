package com.xworkz.controller;

import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.RequestSigningDTO;
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
public class SigninController {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public String signin(@Valid RequestSigningDTO requestSigningDTO, BindingResult bindingResult, Model model){
        System.out.println("Signup Form processing with dto "+requestSigningDTO);

        try{
            if (bindingResult.hasErrors()) {
                model.addAttribute("dto",requestSigningDTO);
                model.addAttribute("errors",bindingResult.getAllErrors());
                return "Signin";
            }

            String result = userService.signin(requestSigningDTO);
            return result;
        }catch (InfoException e){
            System.out.println(e.getMessage());
            model.addAttribute("infoError",e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }

        return "Signin";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@Valid RequestSigningDTO requestSigningDTO, BindingResult bindingResult, Model model){
        System.out.println("Signup Form processing with dto "+requestSigningDTO);

        try{
            if (bindingResult.hasErrors()) {
                model.addAttribute("dto",requestSigningDTO);
                model.addAttribute("errors",bindingResult.getAllErrors());
                return "ResetPassword";
            }

            String result = userService.signin(requestSigningDTO);
            return result;
        }catch (InfoException e){
            System.out.println(e.getMessage());
            model.addAttribute("infoError",e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }


        return "ResetPassword";
    }

}
