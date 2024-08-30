package com.xworkz.controller;


import com.xworkz.dto.DTOListPage;
import com.xworkz.dto.NotificationList;
import com.xworkz.entity.ComplaintDTO;
import com.xworkz.entity.DepartmentAdminDTO;
import com.xworkz.entity.DepartmentDTO;
import com.xworkz.entity.EmployeeDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.*;
import com.xworkz.responseDto.DepartmentNameAndIdResponseDto;
import com.xworkz.responseDto.EmployeeNameAndIdResponseDto;
import com.xworkz.responseDto.ResponseDTO;
import com.xworkz.service.ComplaintService;
import com.xworkz.service.DepartmentAdminService;
import com.xworkz.service.DepartmentService;
import com.xworkz.service.EmployeeService;
import com.xworkz.utils.CSVExport;
import com.xworkz.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/departmentAdmin")
@SessionAttributes({"departmentAdminData"})
public class DepartmentAdminController {

    @Autowired
    private DepartmentAdminService departmentAdminService;

    @Autowired
    private DepartmentService departmentService;

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

    @GetMapping("/home")
    public String getUserHome(Model model){
//        model.addAttribute("link","user");
        return "DepartmentAdmin";
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
            String result = departmentAdminService.signIn(requestSigningDTO, model);
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



    @RequestMapping(value = "/viewDepartmentComplaints/{offset}/{pageSize}", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewDepartmentComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO,@PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize, Model model) {

        System.out.println("Department Admin view complaints process "+requestFilterComplaintDTO);
        try {
            if(offset.isPresent() && offset.get() <= 1) offset = Optional.of(1);

//            System.out.println("view Complaint " + requestFilterComplaintDTO);
            DepartmentAdminDTO departmentAdminDTO = (DepartmentAdminDTO) model.getAttribute("departmentAdminData");
            requestFilterComplaintDTO.setDeptId(departmentAdminDTO.getDepartmentId());
            DTOListPage<ComplaintDTO> complaintDTODTOListPage = complaintService.searchComplaintsForAdmin(requestFilterComplaintDTO, offset.orElse(1), pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE));
            System.out.println(complaintDTODTOListPage);

            model.addAttribute("departmentAdminData",departmentAdminDTO);
            model.addAttribute("complaintsList", complaintDTODTOListPage.getList().get());
            model.addAttribute("downloadCSV","departmentAdmin/viewDepartmentComplaints/downloadCSV");
            model.addAttribute("filter",requestFilterComplaintDTO);

            CommonUtils.setPagination(offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE),"departmentAdmin/viewDepartmentComplaints",complaintDTODTOListPage,model);

        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        return "department/DepartmentViewComplaints";
    }

    @RequestMapping(value = "/viewDepartmentComplaints/downloadCSV/{offset}/{pageSize}", method = {RequestMethod.GET, RequestMethod.POST})
    public void downloadCSVForDepartmentComplaintsData( RequestFilterComplaintDTO requestFilterComplaintDTO, @PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize, HttpServletResponse response, Model model) throws IOException {

        System.out.println("Exporting Department admin with pagination in CSV"+offset.get()+" "+pageSize.get());
        if (offset.isPresent() && offset.get() <= 1) offset = Optional.of(1);

        DepartmentAdminDTO departmentAdminDTO = (DepartmentAdminDTO) model.getAttribute("departmentAdminData");
        requestFilterComplaintDTO.setDeptId(departmentAdminDTO.getDepartmentId());
        DTOListPage<ComplaintDTO> complaintDTODTOListPage = complaintService.searchComplaintsForAdmin(requestFilterComplaintDTO, offset.orElse(1), pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE));

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"data.csv\"");

        CSVExport.sendCSV(response.getWriter(),complaintDTODTOListPage.getList().get(),ComplaintDTO.exportToDepartmentAdmin());
    }

    @RequestMapping(value = "/viewAllEmployees/{offset}/{pageSize}", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewEmployees(RequestFilterEmployeeDTO requestFilterEmployeeDTO, @PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize, Model model) {
        System.out.println("Search Employees initiated by department admin");
        try {

            if(offset.isPresent() && offset.get() <= 1) offset = Optional.of(1);

            DTOListPage<EmployeeDTO> employeeDTODTOListPage = employeeService.searchEmployees(requestFilterEmployeeDTO,offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE),model);

            if (employeeDTODTOListPage.getList().isPresent() && !employeeDTODTOListPage.getList().get().isEmpty()) {
                model.addAttribute("employeeList", employeeDTODTOListPage.getList().get());
            } else {
                model.addAttribute("infoError", "No employees found.");
            }

            model.addAttribute("downloadCSV","departmentAdmin/viewAllEmployees/downloadCSV");
            model.addAttribute("employeesFilter",requestFilterEmployeeDTO);

            CommonUtils.setPagination(offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE),"departmentAdmin/viewAllEmployees",employeeDTODTOListPage,model);
            return "employee/ViewEmployees";

        } catch (Exception e) {
            model.addAttribute("infoError", "An error occurred while fetching the employees.");
        }

        return "employee/ViewEmployees";
    }

    @RequestMapping(value = "/viewAllEmployees/downloadCSV/{offset}/{pageSize}", method = {RequestMethod.GET, RequestMethod.POST})
    public void downloadCSVForDepartmentEmployeesData( RequestFilterEmployeeDTO requestFilterEmployeeDTO, @PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize, HttpServletResponse response, Model model) throws IOException {

        System.out.println("Exporting to Department admin with pagination in CSV"+offset.get()+" "+pageSize.get());
        if (offset.isPresent() && offset.get() <= 1) offset = Optional.of(1);

        DTOListPage<EmployeeDTO> employeeDTODTOListPage = employeeService.searchEmployees(requestFilterEmployeeDTO,offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE),model);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"data.csv\"");

        CSVExport.sendCSV(response.getWriter(),employeeDTODTOListPage.getList().get(),EmployeeDTO.exportToDepartmentAdmin());
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
            return viewDepartmentComplaints(new RequestFilterComplaintDTO(),Optional.empty(), Optional.empty(),model);
        }

        try {
            complaintService.updateComplaintForDepartmentAdmin(requestUpdateDepartmentComplaintByAdminDTO);
            model.addAttribute("successMessage", "Complaint with Id "+ requestUpdateDepartmentComplaintByAdminDTO.getComplaintId()+" updated successfully.");
            return viewDepartmentComplaints(new RequestFilterComplaintDTO(),Optional.empty(), Optional.empty(),model);
        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        return viewDepartmentComplaints(new RequestFilterComplaintDTO(),Optional.empty(), Optional.empty(),model);
    }

    @PostMapping(value = "/register")
    public String registerEmployeeForDept(@Valid RequestDepartmentAdminDTO requestDepartmentAdminDTO, BindingResult bindingResult, Model model) {
        System.out.println("Department Admin register processing with dto " + requestDepartmentAdminDTO);

        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("dto", requestDepartmentAdminDTO);
                model.addAttribute("errors", bindingResult.getAllErrors());
//                model.addAttribute("errorMessage", "Email already exist");
                return "admin/RegisterDepartmentAdmin";
            }

            Boolean result = departmentAdminService.validateAndSave(requestDepartmentAdminDTO,model);
            model.addAttribute("successMessage", " Registration successful!.");
        } catch (InfoException e) {
            System.out.println(e.getMessage());
            model.addAttribute("dto", requestDepartmentAdminDTO);
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("dto", requestDepartmentAdminDTO);
            e.printStackTrace();
        }

        return "admin/RegisterDepartmentAdmin";
    }

    @GetMapping("/check-mobile")
    @ResponseBody
    public ResponseDTO isMobileExists(@RequestParam String mobile){
        System.out.println("Department Controller check mobile number process is initiated for mobile :"+mobile);

        return departmentAdminService.checkMobile(mobile);
    }

    @GetMapping("/check-email")
    @ResponseBody
    public ResponseDTO isEmailExists(@RequestParam String email){
        System.out.println("Department admin Controller check email process is initiated for email :"+email);

        return departmentAdminService.checkMail(email);
    }


    @GetMapping(value = "/departmentAdmins/{offset}/{pageSize}")
    public String viewAllDepartmentAdmins(@PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize, Model model) {
        System.out.println("Fetching All department admins");
        try {

            if(offset.isPresent() && offset.get() <= 1) offset = Optional.of(1);

            DTOListPage<DepartmentAdminDTO> departmentAdminDTOS = departmentAdminService.findAllDepartmentAdmin(offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE));
            model.addAttribute("departmentAdminList",departmentAdminDTOS.getList().get());

            Map<Long,String> departmentMap = departmentService.getAllDepartments().get().stream().collect(Collectors.toMap(
                    DepartmentDTO::getId,
                    DepartmentDTO::getDepartmentName
            ));

            model.addAttribute("departmentMap",departmentMap);

            model.addAttribute("downloadCSV","departmentAdmin/admin/downloadCSV");

            CommonUtils.setPagination(offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE),"departmentAdmin/departmentAdmins",departmentAdminDTOS,model);

            return "admin/AdminViewAllDepartmentAdmin";
        } catch (Exception e) {
            model.addAttribute("infoError", "An error occurred while fetching the employees.");
        }
        return "admin/AdminViewAllDepartmentAdmin";
    }

    @GetMapping(value = "/admin/downloadCSV/{offset}/{pageSize}")
    public void downloadCSVForDepartmentData( @PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize, HttpServletResponse response) throws IOException {

        System.out.println("Exporting Department admin with pagination in CSV"+offset.get()+" "+pageSize.get());
        if (offset.isPresent() && offset.get() <= 1) offset = Optional.of(1);

        DTOListPage<DepartmentAdminDTO> departmentAdminDTOS = departmentAdminService.findAllDepartmentAdmin(offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE));

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"data.csv\"");

        CSVExport.sendCSV(response.getWriter(),departmentAdminDTOS.getList().get(),DepartmentAdminDTO.exportToAdmin());
    }


    @GetMapping("/forgotPasswordPage")
    public String getForgotPasswordPage(Model model) {
        model.addAttribute("userAccess", "departmentAdmin");
        return "ForgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@Valid RequestForgotPasswordDTO requestForgotPasswordDTO, BindingResult bindingResult, Model model) {
        System.out.println("Department Admin Forgot password process initiated with dto " + requestForgotPasswordDTO);
        model.addAttribute("userAccess", "departmentAdmin");

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "ForgotPassword";
        }

        try {
            return departmentAdminService.validateAndSetForgotPassword(requestForgotPasswordDTO, model);
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
//        model.addAttribute("admin", true);
        model.addAttribute("userAccess", "departmentAdmin");


        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("dto", requestResetPasswordDTO);
                model.addAttribute("errors", bindingResult.getAllErrors());
                return "ResetPassword";
            }

            departmentAdminService.validateAndResetPassword(requestResetPasswordDTO);
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


    @GetMapping(value = "/notification")
    @ResponseBody
    public NotificationList<ComplaintDTO> getAdminNotification(Model model) throws IOException {

        System.out.println("Department Admin get notification ");

        try{
            DepartmentAdminDTO departmentAdminDTO = (DepartmentAdminDTO) model.getAttribute("departmentAdminData");
            System.out.println(departmentAdminDTO);
            NotificationList<ComplaintDTO> complaintDTOList = complaintService.getDeptAdminComplaintNotification(departmentAdminDTO.getDepartmentId());
            System.out.println(complaintDTOList);

            return complaintDTOList;
        }catch (Exception e){
            e.printStackTrace();
        }

        return new NotificationList<>();
    }

    @GetMapping("/viewComplaint")
    public String getComplaintDetails(@RequestParam int complaintId ,Model model) {
//        model.addAttribute("admin", true);

        try{
            DepartmentAdminDTO departmentAdminDTO = (DepartmentAdminDTO) model.getAttribute("departmentAdminData");
            ComplaintDTO complaintDTO = departmentAdminService.searchComplaint(complaintId,departmentAdminDTO.getDepartmentId());

            model.addAttribute("complaint",complaintDTO);
            model.addAttribute("viewAccess", "departmentAdmin");


            return "common/ViewComplaint";
        }catch (InfoException e){
            model.addAttribute("infoError", e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }

        return "common/ViewComplaint";
    }

}
