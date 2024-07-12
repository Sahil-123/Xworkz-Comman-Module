package com.xworkz.controller;


import com.xworkz.dto.ComplaintDTO;
import com.xworkz.dto.DepartmentAdminDTO;
import com.xworkz.dto.UserDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.RequestFilterComplaintDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.service.ComplaintService;
import com.xworkz.service.DepartmentAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String viewComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO, Model model) {

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

}
