package com.xworkz.controller;

import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.UserDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.RequestForgotPasswordDTO;
import com.xworkz.requestDto.RequestResetPasswordDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.service.AdminService;
import com.xworkz.service.UserService;
import com.xworkz.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@SessionAttributes("adminData")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @GetMapping("/signinPage")
    public String getSigningPage(Model model) {
//        model.addAttribute("admin", true);
        model.addAttribute("userAccess", "admin");

        return "SignIn";

    }

    @GetMapping("/forgotPasswordPage")
    public String getForgotPasswordPage(Model model) {
//        model.addAttribute("admin", true);
        model.addAttribute("userAccess", "admin");
        return "ForgotPassword";
    }

    @PostMapping("/signin")
    public String singnin(@Valid RequestSigningDTO requestSigningDTO, BindingResult bindingResult, Model model) {
        System.out.println(" Admin Siginin Controller " + requestSigningDTO);
//        model.addAttribute("admin", true);
        model.addAttribute("userAccess", "admin");

        model.addAttribute("dto", requestSigningDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "SignIn";
        }
        try {
            String result = adminService.signin(requestSigningDTO, model);
            return result;
        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return "SignIn";

    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@Valid RequestForgotPasswordDTO requestForgotPasswordDTO, BindingResult bindingResult, Model model) {
        System.out.println("Admin Forgot password process initiated with dto " + requestForgotPasswordDTO);
        model.addAttribute("admin", true);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "ForgotPassword";
        }

        try {
            return adminService.validateAndSetForgotPassword(requestForgotPasswordDTO, model);
        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return "ForgotPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@Valid RequestResetPasswordDTO requestResetPasswordDTO, BindingResult bindingResult, Model model) {
        System.out.println("Reset Form processing with dto " + requestResetPasswordDTO);
        model.addAttribute("admin", true);


        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("dto", requestResetPasswordDTO);
                model.addAttribute("errors", bindingResult.getAllErrors());
                return "ResetPassword";
            }

            adminService.validateAndResetPassword(requestResetPasswordDTO);
            model.addAttribute("successMessage", "Password reset successful! Please login with your new password.");
            return "SignIn";
        } catch (InfoException e) {
            System.out.println(e.getMessage());
            model.addAttribute("dto", requestResetPasswordDTO);
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "ResetPassword";
    }

    @GetMapping("/users/{offset}/{pageSize}")
    public String getUsers(@PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize, Model model){
        System.out.println("Admin get users method is processing the request");

        if(offset.isPresent() && offset.get() <=1) offset = Optional.of(1);

        DTOListPage<UserDTO> userDTODTOListPage = userService.getAllUser(offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE));
        model.addAttribute("userslist", userDTODTOListPage.getList().get());
        int pagesCount = findPageCount(userDTODTOListPage.getCount(),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE));
        String pageURL = "admin/users";
        CommonUtils.setPagination(offset.orElse(1), pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE), pageURL, userDTODTOListPage, pagesCount, model);
        return "component/AdminUsersView";
    }




    private Integer findPageCount(Long totalRecords,Integer pageSize){

        Integer result = Math.toIntExact(totalRecords / pageSize.longValue());

        if(totalRecords % pageSize != 0){
            result++;
        }

        return result;
    }

}
