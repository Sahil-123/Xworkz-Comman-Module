package com.xworkz.controller;

import com.xworkz.requestDto.RequestSigningDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/signinPage")
    public String getSigningPage(Model model){
        model.addAttribute("admin",true);
        return "SignIn";
    }

    @PostMapping("/signin")
    public String singnin(RequestSigningDTO requestSigningDTO, BindingResult bindingResult, Model model){
        System.out.println(" Admin Siginin Controller " +requestSigningDTO );
        model.addAttribute("admin",true);

        if(bindingResult.hasErrors()){
            model.addAttribute("errors",bindingResult.getAllErrors());
            return "SignIn";
        }

        String result = "Signin";

        return result;
    }

}
