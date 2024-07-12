package com.xworkz.controller;


import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.RequestRegisterEmployeeDTO;
import com.xworkz.requestDto.RequestSignupDTO;
import com.xworkz.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/employee")
@SessionAttributes({"employeeData","departmentAdminData"})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/registerForDept")
    public String registerEmployeeForDept(@Valid RequestRegisterEmployeeDTO requestRegisterEmployeeDTO, BindingResult bindingResult, Model model) {
        System.out.println("Employee register processing with dto " + requestRegisterEmployeeDTO);

        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("dto", requestRegisterEmployeeDTO);
                model.addAttribute("errors", bindingResult.getAllErrors());
                model.addAttribute("errorMessage", "Email already exist");
                return "employee/RegisterEmployee";
            }

            Boolean result = employeeService.validateAndSaveByDeptAdmin(requestRegisterEmployeeDTO,model);
            model.addAttribute("successMessage", " Registration successful!.");
        } catch (InfoException e) {
            System.out.println(e.getMessage());
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "employee/RegisterEmployee";
    }

}
