package com.xworkz.controller;


import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.RequestDepartmentDTO;
import com.xworkz.responseDto.DepartmentNameAndIdResponseDto;
import com.xworkz.responseDto.ResponseDTO;
import com.xworkz.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/department")
//@SessionAttributes({"userData"})
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/getAllDepartments")
    @ResponseBody
    public List<DepartmentNameAndIdResponseDto> getDepartments(){
        System.out.println("Controller get all departments");
        List<DepartmentNameAndIdResponseDto> departmentNameAndIdResponseDtos = departmentService.getAllDepartmentsNameAndId();
        return departmentNameAndIdResponseDtos;
    }


    @GetMapping("/check-departmentName")
    @ResponseBody
    public ResponseDTO checkDepartment(@RequestParam String departmentName){
        System.out.println("Controller check department");
        return departmentService.checkDepartmentName(departmentName);
    }

    @PostMapping("/register")
    private String addDepartment(@Valid RequestDepartmentDTO requestDepartmentDTO, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("dto", requestDepartmentDTO);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "admin/RegisterDepartment";
        }

        try{
            Boolean result = departmentService.validateAndSave(requestDepartmentDTO,model);
            model.addAttribute("successMessage", " Registration successful!.");
        } catch (InfoException e) {
            System.out.println(e.getMessage());
            model.addAttribute("dto", requestDepartmentDTO);
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("dto", requestDepartmentDTO);
            e.printStackTrace();
        }

        return "admin/RegisterDepartment";
    }

}
