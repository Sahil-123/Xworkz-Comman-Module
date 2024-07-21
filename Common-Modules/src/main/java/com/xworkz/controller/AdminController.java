package com.xworkz.controller;

import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.ComplaintDTO;
import com.xworkz.entity.DepartmentDTO;
import com.xworkz.entity.UserDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.RequestFilterComplaintDTO;
import com.xworkz.requestDto.RequestForgotPasswordDTO;
import com.xworkz.requestDto.RequestResetPasswordDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.service.AdminService;
import com.xworkz.service.DepartmentService;
import com.xworkz.service.UserService;
import com.xworkz.utils.CSVExport;
import com.xworkz.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
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

    @Autowired
    private DepartmentService departmentService;

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
        model.addAttribute("userAccess", "admin");


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
        model.addAttribute("userAccess", "admin");


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
        model.addAttribute("downloadCSV","admin/downloadCSV");
//        int pagesCount = findPageCount(userDTODTOListPage.getCount(),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE));
        String pageURL = "admin/users";
        CommonUtils.setPagination(offset.orElse(1), pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE), pageURL, userDTODTOListPage, model);
        return "component/AdminUsersView";
    }

    @GetMapping(value = "/downloadCSV/{offset}/{pageSize}")
    public void downloadCSVForUserData( @PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize, HttpServletResponse response) throws IOException {

        System.out.println("Exporting complaints with pagination "+offset.get()+" "+pageSize.get());
        if (offset.isPresent() && offset.get() <= 1) offset = Optional.of(1);

        DTOListPage<UserDTO> userDTODTOListPage = userService.getAllUser(offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE));

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"data.csv\"");

        CSVExport.sendCSV(response.getWriter(),userDTODTOListPage.getList().get(),UserDTO.exportToAdmin());
    }

    @GetMapping("/departments/{offset}/{pageSize}")
    public String getDepartments(@PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize, Model model){
        System.out.println("Admin get departments method is processing the request "+offset.get()+" "+pageSize.get());

        if(offset.isPresent() && offset.get() <=1) offset = Optional.of(1);

        DTOListPage<DepartmentDTO> departmentDTOListPage = departmentService.getAllDepartmentsWithPagination(offset.orElse(1), pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE));
        System.out.println(departmentDTOListPage);

        model.addAttribute("departmentslist", departmentDTOListPage.getList().get());
        model.addAttribute("downloadCSV","admin/departments/downloadCSV");

        String pageURL = "admin/departments";
        CommonUtils.setPagination(offset.orElse(1), pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE), pageURL, departmentDTOListPage, model);
        return "admin/AdminViewAllDepartment";
    }

    @GetMapping(value = "departments/downloadCSV/{offset}/{pageSize}")
    public void downloadCSVForDepartmentData( @PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize, HttpServletResponse response) throws IOException {

        System.out.println("Exporting Departments with pagination in CSV"+offset.get()+" "+pageSize.get());
        if (offset.isPresent() && offset.get() <= 1) offset = Optional.of(1);

        DTOListPage<DepartmentDTO> departmentDTOListPage = departmentService.getAllDepartmentsWithPagination(offset.orElse(1), pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE));

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"data.csv\"");

        CSVExport.sendCSV(response.getWriter(),departmentDTOListPage.getList().get(),DepartmentDTO.exportToAdmin());
    }




//    private Integer findPageCount(Long totalRecords,Integer pageSize){
//        Integer result = Math.toIntExact(totalRecords / pageSize.longValue());
//
//        if(totalRecords % pageSize != 0){
//            result++;
//        }
//        return result;
//    }

}
