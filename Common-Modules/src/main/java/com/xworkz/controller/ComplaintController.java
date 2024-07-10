package com.xworkz.controller;

import com.xworkz.dto.ComplaintDTO;
import com.xworkz.dto.DepartmentDTO;
import com.xworkz.dto.UserDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.RequestComplaintDTO;
import com.xworkz.requestDto.RequestFilterComplaintDTO;
import com.xworkz.requestDto.RequestUpdateComplaintByAdminDTO;
import com.xworkz.requestDto.RequestUpdateComplaintDTO;
import com.xworkz.service.ComplaintService;
import com.xworkz.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/complaints")
@SessionAttributes({"userData"})
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/raiseComplaint")
    public String raiseComplaint(@Valid RequestComplaintDTO requestComplaintDTO, BindingResult bindingResult, Model model) {


        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "user/RaiseUserComplaint";
        }

        try {
            UserDTO userDTO = (UserDTO) model.getAttribute("userData");
            System.out.println("user " + userDTO);
            complaintService.saveComplaint(requestComplaintDTO, userDTO);
            model.addAttribute("successMessage", "Complaint raised successfully.");
        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        return "user/RaiseUserComplaint";
    }

//    @GetMapping("/getComplaints")
//    public String getAllComplaints(){
//
//
//        return "user/RaiseUserComplaint";
//    }

    @RequestMapping(value = "/viewUserComplaints", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO, Model model) {

        try {
            System.out.println("view Complaint " + requestFilterComplaintDTO);
            UserDTO userDTO = (UserDTO) model.getAttribute("userData");
            requestFilterComplaintDTO.setUserId(userDTO.getId());
            Optional<List<ComplaintDTO>> complaintDTOList = complaintService.searchComplaints(requestFilterComplaintDTO);
            System.out.println(complaintDTOList.get());
            model.addAttribute("complaintsList", complaintDTOList.get());
        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        return "complaint/ViewUserComplaints";
    }

//    @RequestMapping(value = "/viewAllComplaints", method = {RequestMethod.GET, RequestMethod.POST})
//    public String viewComplaintsForAdmin(RequestFilterComplaintDTO requestFilterComplaintDTO, Model model) {
//
//        try {
//            System.out.println("view Complaint " + requestFilterComplaintDTO);
//            Optional<List<ComplaintDTO>> complaintDTOList = complaintService.searchComplaintsForAdmin(requestFilterComplaintDTO);
//            System.out.println(complaintDTOList.get());
//            model.addAttribute("complaintsList", complaintDTOList.get().subList(1,5));
//
//
//            Optional<List<DepartmentDTO>> departmentDTOList = departmentService.getAllDepartments();
//            System.out.println(departmentDTOList.get());
//            model.addAttribute("departments", departmentDTOList.get());
//            System.out.println("response is set successfully");
//
//            return "admin/AdminViewComplaints";
//
//        } catch (InfoException e) {
//            model.addAttribute("infoError", e.getMessage());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println(e.getCause());
//            e.printStackTrace();
//        }
//
//
//        return "admin/AdminViewComplaints";
//    }

    @RequestMapping(value = "/viewAllComplaints", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewComplaintsForAdmin(RequestFilterComplaintDTO requestFilterComplaintDTO, Model model) {
        try {
            Optional<List<ComplaintDTO>> complaintDTOList = complaintService.searchComplaintsForAdmin(requestFilterComplaintDTO);

            if (complaintDTOList.isPresent() && !complaintDTOList.get().isEmpty()) {
                model.addAttribute("complaintsList", complaintDTOList.get());
            } else {
                model.addAttribute("infoError", "No complaints found.");
            }

            return "admin/AdminViewComplaints";

        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("infoError", "An error occurred while fetching the complaints.");
        }

        return "admin/AdminViewComplaints";
    }


    @GetMapping("/updateComplaintPage")
    public String updateComplaintPage(@RequestParam Long id,Model model){
        System.out.println("update complaint process is initiated");

        try {
            Optional<ComplaintDTO> complaintDTO = complaintService.findComplaintById(id);

            if(complaintDTO.isPresent()){
                model.addAttribute("complaintData",complaintDTO.get());
                model.addAttribute("edit",true);
            }else{
                model.addAttribute("infoError","Complaint records not found");
            }

        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        return "user/RaiseUserComplaint";
    }

    @PostMapping("/updateComplaint")
    public String updateComplaint(@Valid RequestUpdateComplaintDTO requestUpdateComplaintDTO, BindingResult bindingResult,Model model){
        System.out.println("update complaint process initiated "+ requestUpdateComplaintDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "user/RaiseUserComplaint";
        }

        try{
            if(complaintService.updateComplaint(requestUpdateComplaintDTO)){
                model.addAttribute("successMessage", "Complaint updated successfully.");
                return viewComplaints(new RequestFilterComplaintDTO(),model);
            }else{
                model.addAttribute("infoError", "Something is wrong. Update is not successful");
            }
        }catch (InfoException e){
            model.addAttribute("infoError", e.getMessage());
        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
            model.addAttribute("infoError", e.getMessage());

        }

        model.addAttribute("edit",true);
        return "user/RaiseUserComplaint";
    }

    @PostMapping("/adminUpdateComplaint")
    public String updateComplaintDepartmentAndStatusByAdmin(RequestUpdateComplaintByAdminDTO requestUpdateComplaintByAdminDTO,BindingResult bindingResult, Model model){
        System.out.println("update complaint process is initiated by admin "+requestUpdateComplaintByAdminDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return viewComplaintsForAdmin(new RequestFilterComplaintDTO(),model);
        }

        try {
            complaintService.updateComplaintForAdmin(requestUpdateComplaintByAdminDTO);
            model.addAttribute("successMessage", "Complaint with Id "+ requestUpdateComplaintByAdminDTO.getComplaintId()+" updated successfully.");
            return viewComplaintsForAdmin(new RequestFilterComplaintDTO(),model);
        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        return viewComplaintsForAdmin(new RequestFilterComplaintDTO(),model);
    }

}