package com.xworkz.controller;


import com.xworkz.responseDto.DepartmentNameAndIdResponseDto;
import com.xworkz.responseDto.ResponseDTO;
import com.xworkz.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

}
