package com.xworkz.controller;


import com.xworkz.dto.DTOListPage;
import com.xworkz.dto.NotificationList;
import com.xworkz.entity.*;
import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.*;
import com.xworkz.responseDto.ResponseDTO;
import com.xworkz.responseDto.ResponseResolveComplaintDto;
import com.xworkz.service.ComplaintService;
import com.xworkz.service.EmployeeService;
import com.xworkz.utils.CSVExport;
import com.xworkz.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
@SessionAttributes({"employeeData", "departmentAdminData","employeeImageData"})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ComplaintService complaintService;


    @GetMapping("/signinPage")
    public String getSigningPage(Model model) {
//        model.addAttribute("admin", true);
        model.addAttribute("userAccess", "employee");
        return "SignIn";
    }

    @GetMapping("/home")
    public String getUserHome(Model model){
//        model.addAttribute("link","user");
        return "Employee";
    }


    @PostMapping("/signin")
    public String signin(@Valid RequestSigningDTO requestSigningDTO, BindingResult bindingResult, Model model) {
        System.out.println("Employee Signin Form processing with dto " + requestSigningDTO);
        model.addAttribute("dto", requestSigningDTO);
        model.addAttribute("userAccess", "employee");

        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("errors", bindingResult.getAllErrors());
                return "SignIn";
            }

            String result = employeeService.signin(requestSigningDTO, model);
            return result;
        } catch (InfoException e) {
            System.out.println(e.getMessage());
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "SignIn";
    }

    @GetMapping("/forgotPasswordPage")
    public String getForgotPasswordPage(Model model) {
//        model.addAttribute("admin", true);
        model.addAttribute("userAccess", "employee");
        return "ForgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@Valid RequestForgotPasswordDTO requestForgotPasswordDTO, BindingResult bindingResult, Model model) {
        System.out.println("Employee Forgot password process initiated with dto " + requestForgotPasswordDTO);
        model.addAttribute("userAccess", "employee");

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "ForgotPassword";
        }

        try {
            return employeeService.validateAndSetForgotPassword(requestForgotPasswordDTO, model);
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
        System.out.println("Employee Reset Form processing with dto " + requestResetPasswordDTO);
        model.addAttribute("userAccess", "employee");

        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("dto", requestResetPasswordDTO);
                model.addAttribute("errors", bindingResult.getAllErrors());
                return "ResetPassword";
            }

            employeeService.validateAndResetPassword(requestResetPasswordDTO);
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


    @PostMapping(value = "/registerForDept")
    public String registerEmployeeForDept(@Valid RequestRegisterEmployeeDTO requestRegisterEmployeeDTO, BindingResult bindingResult, Model model) {
        System.out.println("Employee register processing with dto " + requestRegisterEmployeeDTO);

        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("dto", requestRegisterEmployeeDTO);
                model.addAttribute("errors", bindingResult.getAllErrors());
//                model.addAttribute("errorMessage", "Email already exist");
                return "employee/RegisterEmployee";
            }

            Boolean result = employeeService.validateAndSaveByDeptAdmin(requestRegisterEmployeeDTO, model);
            model.addAttribute("successMessage", " Registration successful!.");
        } catch (InfoException e) {
            System.out.println(e.getMessage());
            model.addAttribute("dto", requestRegisterEmployeeDTO);
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("dto", requestRegisterEmployeeDTO);
            e.printStackTrace();
        }

        return "employee/RegisterEmployee";
    }

    @GetMapping("/check-mobile")
    @ResponseBody
    public ResponseDTO isMobileExists(@RequestParam String mobile) {
        System.out.println("Employee Controller check mobile number process is initiated for mobile :" + mobile);

        return employeeService.checkMobile(mobile);
    }

    @GetMapping("/check-email")
    @ResponseBody
    public ResponseDTO isEmailExists(@RequestParam String email) {
        System.out.println("Employee Controller check email process is initiated for email :" + email);

        return employeeService.checkMail(email);
    }

    @RequestMapping(value = "/viewEmployeeComplaints/{offset}/{pageSize}", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewEmployeeComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO, @PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize ,Model model) {

        System.out.println("Employee view complaints process for not resolved complaint" + requestFilterComplaintDTO);
        try {
            if(offset.isPresent() && offset.get() <= 1) offset = Optional.of(1);

//            System.out.println("view Complaint " + requestFilterComplaintDTO);
            EmployeeDTO employeeDTO = (EmployeeDTO) model.getAttribute("employeeData");
            DTOListPage<ComplaintDTO> complaintDTODTOListPage = complaintService.searchNotResolvedComplaintsForEmployee(requestFilterComplaintDTO,offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE), employeeDTO);
            System.out.println(complaintDTODTOListPage);

            model.addAttribute("complaintsList", complaintDTODTOListPage.getList().get());
            model.addAttribute("downloadCSV","employee/viewEmployeeComplaints/downloadCSV");
            model.addAttribute("employeeFilter",requestFilterComplaintDTO);

            CommonUtils.setPagination(offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE),"employee/viewEmployeeComplaints",complaintDTODTOListPage,model);
        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        model.addAttribute("status", CommonUtils.NOT_RESOLVED);
        return "employee/EmployeeViewComplaints";
    }

    @RequestMapping(value = "/viewEmployeeComplaints/downloadCSV/{offset}/{pageSize}", method = {RequestMethod.GET, RequestMethod.POST})
    public void downloadCSVForDepartmentEmployeesData(RequestFilterComplaintDTO requestFilterComplaintDTO, @PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize, HttpServletResponse response, Model model) throws IOException {

        System.out.println("Exporting to Department admin with pagination in CSV"+offset.get()+" "+pageSize.get());
        if (offset.isPresent() && offset.get() <= 1) offset = Optional.of(1);

        EmployeeDTO employeeDTO = (EmployeeDTO) model.getAttribute("employeeData");
        DTOListPage<ComplaintDTO> complaintDTODTOListPage = complaintService.searchNotResolvedComplaintsForEmployee(requestFilterComplaintDTO,offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE), employeeDTO);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"data.csv\"");

        CSVExport.sendCSV(response.getWriter(),complaintDTODTOListPage.getList().get(),ComplaintDTO.exportToEmployee());
    }

    @RequestMapping(value = "/viewEmployeeResolvedComplaints/{offset}/{pageSize}", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewEmployeeResolvedComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO,@PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize, Model model) {

        System.out.println("Employee view complaints process for resolved complaints. " + requestFilterComplaintDTO);
        try {

            if(offset.isPresent() && offset.get() <= 1) offset = Optional.of(1);

//            System.out.println("view Complaint " + requestFilterComplaintDTO);
            EmployeeDTO employeeDTO = (EmployeeDTO) model.getAttribute("employeeData");
            DTOListPage<ComplaintDTO> complaintDTODTOListPage = complaintService.searchResolvedComplaintsForEmployee(requestFilterComplaintDTO,offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE) ,employeeDTO);
            System.out.println(complaintDTODTOListPage);

            model.addAttribute("complaintsList", complaintDTODTOListPage.getList().get());
            model.addAttribute("downloadCSV","employee/viewEmployeeResolvedComplaints/downloadCSV");
            model.addAttribute("employeeFilter",null);

            CommonUtils.setPagination(offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE),"employee/viewEmployeeResolvedComplaints",complaintDTODTOListPage,model);

        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        model.addAttribute("status", CommonUtils.RESOLVED);
        return "employee/EmployeeViewComplaints";
    }

    @RequestMapping(value = "/viewEmployeeResolvedComplaints/downloadCSV/{offset}/{pageSize}", method = {RequestMethod.GET, RequestMethod.POST})
    public void downloadCSVForDepartmentEmployeesResolvedData(RequestFilterComplaintDTO requestFilterComplaintDTO, @PathVariable Optional<Integer> offset, @PathVariable Optional<Integer> pageSize, HttpServletResponse response, Model model) throws IOException {

        System.out.println("Exporting to Department admin with pagination in CSV"+offset.get()+" "+pageSize.get());
        if (offset.isPresent() && offset.get() <= 1) offset = Optional.of(1);

        EmployeeDTO employeeDTO = (EmployeeDTO) model.getAttribute("employeeData");
        DTOListPage<ComplaintDTO> complaintDTODTOListPage = complaintService.searchResolvedComplaintsForEmployee(requestFilterComplaintDTO,offset.orElse(1),pageSize.orElse(CommonUtils.DEFAULT_PAGE_SIZE) ,employeeDTO);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"data.csv\"");

        CSVExport.sendCSV(response.getWriter(),complaintDTODTOListPage.getList().get(),ComplaintDTO.exportToEmployeeWithResolved());
    }

    @GetMapping(value = "/resolveComplaintOtp")
    @ResponseBody
    public ResponseResolveComplaintDto getResolveComplaintOTP(@RequestParam Long complaintId, Model model) {
        System.out.println("Empliyee otp generation process is initiated " + complaintId);
        System.out.println("complaint Id " + complaintId);

        if (complaintId < 0) {
            return new ResponseResolveComplaintDto(false, "Complaint Id should be valid");
        }

        try {
            EmployeeDTO employeeDTO = (EmployeeDTO) model.getAttribute("employeeData");
            return employeeService.generateOTP(employeeDTO, complaintId);
        } catch (InfoException infoException) {
            return new ResponseResolveComplaintDto(false, infoException.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseResolveComplaintDto();
    }

    @PostMapping(value = "/resolveComplaint")
    @ResponseBody
    public ResponseResolveComplaintDto resolveComplaint(@RequestBody @Valid RequestResolveComplaintDTO requestResolveComplaintDTO,
                                                        BindingResult bindingResult,
                                                        Model model) {
        System.out.println("Employee resolve complaint process is initiated " + requestResolveComplaintDTO);

        if (bindingResult.hasErrors()) {
            String result = "";

            StringBuilder sb = new StringBuilder();

            for (ObjectError s : bindingResult.getAllErrors()) {
                sb.append(s.getDefaultMessage()).append(",");
            }

            result = sb.deleteCharAt(sb.length() - 1).toString();

            return new ResponseResolveComplaintDto(false, result);
        }

        try {

            EmployeeDTO employeeDTO = (EmployeeDTO) model.getAttribute("employeeData");

            return employeeService.resolveComplaint(requestResolveComplaintDTO, employeeDTO);

        } catch (InfoException e) {
            return new ResponseResolveComplaintDto(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseResolveComplaintDto(false, "Something goes wrong. Please try again letter.");
    }


    @PostMapping(value = "/resolveOtherStatusComplaint")
    @ResponseBody
    public ResponseResolveComplaintDto updateOtherStatusComplaint(@RequestBody @Valid RequestOtherStatusComplaintDTO requestResolveComplaintDTO,
                                                                  BindingResult bindingResult,
                                                                  Model model) {
        System.out.println("Employee update other status complaint process is initiated " + requestResolveComplaintDTO);

        if (bindingResult.hasErrors()) {
            String result = "";

            StringBuilder sb = new StringBuilder();

            for (ObjectError s : bindingResult.getAllErrors()) {
                sb.append(s.getDefaultMessage()).append(",");
            }

            result = sb.deleteCharAt(sb.length() - 1).toString();

            return new ResponseResolveComplaintDto(false, result);
        }

        try {

            EmployeeDTO employeeDTO = (EmployeeDTO) model.getAttribute("employeeData");
            return employeeService.resolveOtherStatusComplaint(requestResolveComplaintDTO, employeeDTO);

        } catch (InfoException e) {
            return new ResponseResolveComplaintDto(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseResolveComplaintDto(false, "Something goes wrong. Please try again letter.");
    }

    @GetMapping("/editProfilePage")
    public String editProfilePage(Model model) {

//        EmployeeDTO employeeDTO = (EmployeeDTO) model.getAttribute("employeeData");
//        model.addAttribute("userData",employeeDTO);
        return "employee/EmployeeEditProfile";
    }

    @PostMapping("/editProfile")
    public String editProfile(RequestEmployeeProfileDTO requestEmployeeProfileDTO, BindingResult bindingResult, Model model) {
        System.out.println("Employee Edit profile process is initiated." + requestEmployeeProfileDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "employee/EmployeeEditProfile";
        }

        try {
            employeeService.editProfile(requestEmployeeProfileDTO, model);
        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }


        model.addAttribute("successMessage", "Profile update is completed");
        return "employee/EmployeeEditProfile";
    }

    @GetMapping("/profileImage")
    public void getImage(ServletResponse response, Model model) throws IOException {
        System.out.println("Employee Image fetching process initiated");

        try {
            EmployeeImageDTO employeeImageData = (EmployeeImageDTO) model.getAttribute("employeeImageData");
            System.out.println("Image "+employeeImageData);
            if (employeeImageData == null) {
                throw new InfoException("Image data not found in model");
            }

            // Example file path, replace with your actual image file path
            Path path = Paths.get("D:\\Xworkz-Comman-Module\\uploadedImages\\" + employeeImageData.getImageName());

            // Set the content type and buffer size
            response.setContentType(employeeImageData.getImageType());
            response.setBufferSize(employeeImageData.getImageSize());

            // Write the file to the response output stream
            OutputStream outputStream = response.getOutputStream();
            byte[] byteData = Files.readAllBytes(path);
            outputStream.write(byteData);
            outputStream.flush();
        }
        catch (InfoException infoException){
            model.addAttribute("infoError", infoException.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @GetMapping(value = "/notification")
    @ResponseBody
    public NotificationList<ComplaintDTO> getAdminNotification(Model model) throws IOException {

        System.out.println("Employee get notification ");

        try{
            EmployeeDTO employeeDTO = (EmployeeDTO) model.getAttribute("employeeData");
            System.out.println(employeeDTO);
            NotificationList<ComplaintDTO> complaintDTOList = complaintService.getUserComplaintNotification(employeeDTO.getId(),employeeDTO.getDepartmentId());
            System.out.println(complaintDTOList);
            return complaintDTOList;
        }catch (Exception e){
            e.printStackTrace();
        }

        return new NotificationList<ComplaintDTO>();
    }

    @GetMapping("/viewComplaint")
    public String getComplaintDetails(@RequestParam int complaintId ,Model model) {
//        model.addAttribute("admin", true);

        try{
            EmployeeDTO employeeDTO = (EmployeeDTO) model.getAttribute("employeeData");
            ComplaintDTO complaintDTO = employeeService.searchComplaint(complaintId,employeeDTO.getId(),employeeDTO.getDepartmentId());

            model.addAttribute("complaint",complaintDTO);
            model.addAttribute("viewAccess", "employee");


            return "common/ViewComplaint";
        }catch (InfoException e){
            model.addAttribute("infoError", e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }

        return "common/ViewComplaint";
    }


}
