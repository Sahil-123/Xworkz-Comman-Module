package com.xworkz.controller;

import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/signinPage")
    public String getSigningPage(Model model){
        model.addAttribute("admin",true);
        return "SignIn";
    }

    @PostMapping("/signin")
    public String singnin(RequestSigningDTO requestSigningDTO, BindingResult bindingResult, Model model){
        System.out.println(" Admin Siginin Controller " +requestSigningDTO );
        model.addAttribute("admin",true);
        model.addAttribute("dto",requestSigningDTO);

        if(bindingResult.hasErrors()){
            model.addAttribute("errors",bindingResult.getAllErrors());
            return "SignIn";
        }

        String result = adminService.validateAdmin(requestSigningDTO,model);
        return result;
    }

}
