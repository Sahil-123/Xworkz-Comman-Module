package com.xworkz.controller;


import com.xworkz.dto.ComplaintDTO;
import com.xworkz.dto.DepartmentAdminDTO;
import com.xworkz.dto.EmployeeDTO;
import com.xworkz.dto.UserDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.*;
import com.xworkz.responseDto.EmployeeNameAndIdResponseDto;
import com.xworkz.service.ComplaintService;
import com.xworkz.service.DepartmentAdminService;
import com.xworkz.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/departmentAdmin")
@SessionAttributes({"departmentAdminData"})
public class DepartmentAdminController {

    @Autowired
    private DepartmentAdminService departmentAdminService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/signinPage")
    public String getSigningPage(Model model) {
        model.addAttribute("admin", true);
        model.addAttribute("userAccess", "departmentAdmin");
        return "SignIn";
    }

    @PostMapping("/signin")
    public String singnin(@Valid RequestSigningDTO requestSigningDTO, BindingResult bindingResult, Model model) {
        System.out.println(" Department Admin Sigin Controller " + requestSigningDTO);
        model.addAttribute("userAccess", "departmentAdmin");
        model.addAttribute("dto", requestSigningDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "SignIn";
        }
        try {
            System.out.println("department admin login");
            String result = departmentAdminService.signin(requestSigningDTO, model);
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



    @RequestMapping(value = "/viewDepartmentComplaints", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewDepartmentComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO, Model model) {

        System.out.println("Department Admin view complaints process "+requestFilterComplaintDTO);
        try {
//            System.out.println("view Complaint " + requestFilterComplaintDTO);
            DepartmentAdminDTO departmentAdminDTO = (DepartmentAdminDTO) model.getAttribute("departmentAdminData");
            requestFilterComplaintDTO.setDeptId(departmentAdminDTO.getDepartmentId());
            Optional<List<ComplaintDTO>> complaintDTOList = complaintService.searchComplaintsForAdmin(requestFilterComplaintDTO);
            System.out.println(complaintDTOList.get());

            model.addAttribute("departmentAdminData",departmentAdminDTO);
            model.addAttribute("complaintsList", complaintDTOList.get());
        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        return "department/DepartmentViewComplaints";
    }

    @RequestMapping(value = "/viewAllEmployees", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewEmployees(RequestFilterEmployeeDTO requestFilterEmployeeDTO, Model model) {
        try {
            Optional<List<EmployeeDTO>> employeeDTOList = employeeService.searchEmployees(requestFilterEmployeeDTO,model);

            if (employeeDTOList.isPresent() && !employeeDTOList.get().isEmpty()) {
                model.addAttribute("employeeList", employeeDTOList.get());
            } else {
                model.addAttribute("infoError", "No employees found.");
            }

            return "employee/ViewEmployees";

        } catch (Exception e) {
            model.addAttribute("infoError", "An error occurred while fetching the employees.");
        }

        return "employee/ViewEmployees";
    }

    @GetMapping(value = "/getDepartmentEmployees")
    @ResponseBody
    public List<EmployeeNameAndIdResponseDto> viewEmployeesRestService(Model model) {
        try {
            List<EmployeeNameAndIdResponseDto> employeeNameAndIdResponseDtos = employeeService.searchEmployeesRestService(model);
            return employeeNameAndIdResponseDtos;
        } catch (Exception e) {
            model.addAttribute("infoError", "An error occurred while fetching the employees.");
        }
        return Collections.emptyList();
    }


    @PostMapping("/departmentAdminUpdateComplaint")
    public String updateComplaintDepartmentAndStatusByAdmin(RequestUpdateDepartmentComplaintByAdminDTO requestUpdateDepartmentComplaintByAdminDTO, BindingResult bindingResult, Model model){
        System.out.println("update complaint by department admin process is initiated by admin "+requestUpdateDepartmentComplaintByAdminDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return viewDepartmentComplaints(new RequestFilterComplaintDTO(),model);
        }

        try {
            complaintService.updateComplaintForDepartmentAdmin(requestUpdateDepartmentComplaintByAdminDTO);
            model.addAttribute("successMessage", "Complaint with Id "+ requestUpdateDepartmentComplaintByAdminDTO.getComplaintId()+" updated successfully.");
            return viewDepartmentComplaints(new RequestFilterComplaintDTO(),model);
        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        return viewDepartmentComplaints(new RequestFilterComplaintDTO(),model);
    }

}
