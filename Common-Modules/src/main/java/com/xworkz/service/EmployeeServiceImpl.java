package com.xworkz.service;

import com.xworkz.dto.*;
import com.xworkz.exceptions.InfoException;
import com.xworkz.repository.ComplaintRepository;
import com.xworkz.repository.DepartmentRepository;
import com.xworkz.repository.EmployeeRepository;
import com.xworkz.repository.UserRepository;
import com.xworkz.requestDto.*;
import com.xworkz.responseDto.EmployeeNameAndIdResponseDto;
import com.xworkz.responseDto.ResponseDTO;
import com.xworkz.responseDto.ResponseDataDTO;
import com.xworkz.responseDto.ResponseOTPDto;
import com.xworkz.utils.CustomeMailSender;
import com.xworkz.utils.OTPGenerator;
import com.xworkz.utils.PasswordGenerator;
import com.xworkz.utils.TimeConversion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomeMailSender mailSender;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public String signin(RequestSigningDTO requestSigningDTO, Model model) {
        System.out.println("Employee Sigin process is initiated with request signin dto " + requestSigningDTO);

        Optional<EmployeeDTO> employeeDTOOptional = employeeRepository.findByEmail(requestSigningDTO.getEmail());

        if (employeeDTOOptional.isPresent()) {
            EmployeeDTO employeeDTO = employeeDTOOptional.get();

            if(employeeDTO.isLock()){
                throw new InfoException(" Your account is Locked. Please Reset your password.");
            }

            if (employeeDTO.getPassword().equals(requestSigningDTO.getPassword())) {
                if (employeeDTO.getLoginCount() == 0 || employeeDTO.getFailedAttempts() == 3) {
                    return "ResetPassword";
                }
                employeeDTO.setFailedAttempts(0);

//                save updated counts to database
                employeeRepository.update(employeeDTO);
                model.addAttribute("employeeData", employeeDTO);

//                Optional<ImageDTO> imageDTO = imageService.findActiveImageByUserId(employeeDTO.getId());
//                if(imageDTO.isPresent()){
//                    model.addAttribute("imageData",imageDTO.get());
//                }

                return "Employee";
            }
            else {

//                3 time wrong attempts allowed within the 1 hour after that it will be allowed
//                3 attempts if previous attempts are within 3 then only allowed more 3 attempts in next 1 hour

                long duration = TimeConversion.getDuration(employeeDTO.getFailedAttemptsDateTime());
                System.out.println("trying to check the attempts");
                if (duration < 1L && employeeDTO.getFailedAttempts() < 3) {
                    System.out.println("under valid attempts : " + employeeDTO.getFailedAttempts());
                    if (employeeDTO.getFailedAttempts() == 0) {
                        employeeDTO.setFailedAttemptsDateTime(LocalDateTime.now());
                    }
                    employeeDTO.setFailedAttempts(employeeDTO.getFailedAttempts() + 1);

                    if(employeeDTO.getFailedAttempts() == 3){
                        employeeDTO.setLock(true);
                    }
//                    save updated counts to database.
                    employeeRepository.update(employeeDTO);

                } else if (duration >= 1L) {
                    System.out.println("checking under specified duration attempts are valid or not attempt = "+employeeDTO.getFailedAttempts());
                    if (employeeDTO.getFailedAttempts() < 3) {
                        System.out.println("attempts are valid under 3 after an hours so resting the time and count");
                        employeeDTO.setFailedAttemptsDateTime(LocalDateTime.now());
                        employeeDTO.setFailedAttempts(1);

//                    save updated counts to database.
                        employeeRepository.update(employeeDTO);

                    }else{
                        throw new InfoException("Your have exceeded login attempts. Your account is Locked. Please Reset your password.");
                    }
                } else {
                    throw new InfoException("Your have exceeded login attempts. Your account is Locked. Please Reset your password.");
                }
            }
        }
        throw new InfoException("Invalid Email or Password");
    }

    @Override
    public boolean validateAndResetPassword(RequestResetPasswordDTO requestResetPasswordDTO) {
        System.out.println("Employee reset password process is initiated with request password dto " + requestResetPasswordDTO);

        Optional<EmployeeDTO> employeeDTOOptional = employeeRepository.findByEmail(requestResetPasswordDTO.getEmail());

        if (!requestResetPasswordDTO.getNewPassword().equals(requestResetPasswordDTO.getConfirmPassword())) {
            throw new InfoException("New Password and Conform Password must have same value.");
        }

        if (employeeDTOOptional.isPresent()) {
            EmployeeDTO employeeDTO = employeeDTOOptional.get();

            if(employeeDTO.isLock()){
                throw new InfoException("Please follow forgot password process in order to unlock and reset an account.");
            }

            if(employeeDTO.getLoginCount() == 0 || employeeDTO.getFailedAttempts() == 3){
                if (employeeDTO.getPassword().equals(requestResetPasswordDTO.getPassword())) {
                    employeeDTO.setPassword(requestResetPasswordDTO.getNewPassword());
                    employeeDTO.setFailedAttempts(0);
                    if(employeeDTO.getLoginCount() == 0) employeeDTO.setLoginCount(1);

                    return employeeRepository.update(employeeDTO);
                }
            }else {
                throw new InfoException("Not allowed to reset the password");
            }

        }

        throw new InfoException("Invalid Email or Password");
    }

    @Override
    public String validateAndSetForgotPassword(RequestForgotPasswordDTO requestForgotPasswordDTO, Model model) {
        System.out.println("Admin forgot password process is initiated with request email dto " + requestForgotPasswordDTO);

        Optional<EmployeeDTO> employeeDTOOptional = employeeRepository.findByEmail(requestForgotPasswordDTO.getEmail());

        if (employeeDTOOptional.isPresent()) {
            EmployeeDTO employeeDTO = employeeDTOOptional.get();
            String generatePassword = PasswordGenerator.generatePassword();
            employeeDTO.setFailedAttempts(3);
            employeeDTO.setPassword(generatePassword);
            employeeDTO.setLock(false);
            employeeRepository.update(employeeDTO);

            mailSender.sendResetPasswordMail(employeeDTO.getEmail(),employeeDTO.getPassword());
            model.addAttribute("successMessage","We have sent an email containing a temporary password to your registered email address. You can use this temporary password to log in and reset your password.");
            return "ForgotPassword";
        }

        throw new InfoException("Invalid Email!");
    }


    @Override
    public Boolean validateAndSaveByDeptAdmin(RequestRegisterEmployeeDTO requestRegisterEmployeeDTO, Model model) {

        System.out.println("Employee Service process is initiated using DTO: " + requestRegisterEmployeeDTO);

        Optional<EmployeeDTO> employeeDTOOptional = employeeRepository.findByEmail(requestRegisterEmployeeDTO.getEmail());

        if (employeeDTOOptional.isPresent()) {
            System.out.println(employeeDTOOptional.get());
            throw new InfoException("Email already exist");
        }

        Optional<List<EmployeeDTO>> employeeDTOList = employeeRepository.findByEmployeeMobile(requestRegisterEmployeeDTO.getMobile());

        if (employeeDTOList.isPresent() && !employeeDTOList.get().isEmpty()) {
            System.out.println(employeeDTOList.get());
            throw new InfoException("Mobile number already exist");
        }

        DepartmentAdminDTO departmentAdminDTO = (DepartmentAdminDTO) model.getAttribute("departmentAdminData");
        Optional<DepartmentDTO> departmentDTO = departmentRepository.findById(departmentAdminDTO.getDepartmentId());


        if(!departmentDTO.isPresent()){
            throw new InfoException("Department not found.");
        }

        EmployeeDTO employeeDTO = modelMapper.map(requestRegisterEmployeeDTO, EmployeeDTO.class);

        employeeDTO.setDepartmentId(departmentAdminDTO.getDepartmentId());
        employeeDTO.setCreatedBy(employeeDTO.getFname() + " " + employeeDTO.getLname());
        employeeDTO.setCreatedDate(LocalDateTime.now());
        employeeDTO.setPassword(PasswordGenerator.generatePassword());
        System.out.println(employeeDTO);
        Boolean result = employeeRepository.save(employeeDTO);
        mailSender.sendEmployeeRegisterMail(employeeDTO.getEmail(), employeeDTO.getPassword(),departmentDTO.get().getDepartmentName());
        return result;
    }

    @Override
    public ResponseDTO checkMobile(String mobile) {
        boolean check = employeeRepository.checkMobile(mobile);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("200");
        responseDTO.setData(new ResponseDataDTO(check));
        return responseDTO;
    }

    @Override
    public ResponseDTO checkMail(String mail) {
        boolean check = employeeRepository.checkEmail(mail);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("200");
        responseDTO.setData(new ResponseDataDTO(check));
        return responseDTO;
    }

    @Override
    public Optional<List<EmployeeDTO>> searchEmployees(RequestFilterEmployeeDTO requestFilterEmployeeDTO,Model model) {
        System.out.println("Search Employees in service " + requestFilterEmployeeDTO);
        DepartmentAdminDTO departmentAdminDTO = (DepartmentAdminDTO) model.getAttribute("departmentAdminData");
        EmployeeDTO employeeDTO = modelMapper.map(requestFilterEmployeeDTO, EmployeeDTO.class);

        employeeDTO.setDepartmentId(departmentAdminDTO.getDepartmentId());
        return employeeRepository.searchAllEmployees(employeeDTO);
    }

    @Override
    public List<EmployeeNameAndIdResponseDto> searchEmployeesRestService(Model model) {
        RequestFilterEmployeeDTO requestFilterEmployeeDTO = new RequestFilterEmployeeDTO();
        System.out.println("Search Employees in service " + requestFilterEmployeeDTO);
        DepartmentAdminDTO departmentAdminDTO = (DepartmentAdminDTO) model.getAttribute("departmentAdminData");
        EmployeeDTO employeeDTO = modelMapper.map(requestFilterEmployeeDTO, EmployeeDTO.class);

        employeeDTO.setDepartmentId(departmentAdminDTO.getDepartmentId());
        Optional<List<EmployeeDTO>> employeeDTOList = employeeRepository.searchAllEmployees(employeeDTO);

        if(employeeDTOList.isPresent()){
            List<EmployeeNameAndIdResponseDto> employeeNameAndIdResponseDtos = employeeDTOList.get().stream().map(e->new EmployeeNameAndIdResponseDto(e.getId(),e.getFname()+" "+e.getLname())).collect(Collectors.toList());
            return employeeNameAndIdResponseDtos;
        }

        return Collections.emptyList();
    }


    @Override
    public ResponseOTPDto generateOTP(EmployeeDTO employeeDTO, Long complaintId) {

        ComplaintDTO searchComplaint = new ComplaintDTO();
        searchComplaint.setEmpId(employeeDTO.getId());
        searchComplaint.setDeptId(employeeDTO.getDepartmentId());
        searchComplaint.setId(complaintId);
        Optional<List<ComplaintDTO>> complaintDTOList =complaintRepository.searchAllComplaintsForAdmin(searchComplaint);

        if(complaintDTOList.isPresent() && !complaintDTOList.get().isEmpty()){
            ComplaintDTO complaintDTO = complaintDTOList.get().get(0);

            String otp = OTPGenerator.generateOTP();

            complaintDTO.setOtp(otp);
            complaintDTO.setOtptime(LocalDateTime.now());

            Optional<UserDTO> userDTO = userRepository.findById(complaintDTO.getUserId());

            if (!userDTO.isPresent()){
                System.out.println("user not found");
                return new ResponseOTPDto(false,"Something is wrong. Please try after some time. ");
            }

            Boolean status = complaintRepository.update(complaintDTO);

            if(status){
                mailSender.sendComplaintResolutionMail(userDTO.get().getEmail(),otp);
                return new ResponseOTPDto(true,"OTP has been sent to user please check and verify to resolve the issue.");

            }else {
                return new ResponseOTPDto(false,"Something is wrong. Please try after some time. ");
            }

        }else {
            return new ResponseOTPDto(false, "Complaint Data not found");
        }

    }



}
