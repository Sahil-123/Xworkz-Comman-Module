package com.xworkz.service;

import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.*;
import com.xworkz.exceptions.InfoException;
import com.xworkz.repository.ComplaintRepository; // Assuming you have this repository
import com.xworkz.repository.DepartmentAdminRepository;
import com.xworkz.repository.DepartmentRepository;
import com.xworkz.requestDto.*;
import com.xworkz.responseDto.ResponseDTO;
import com.xworkz.responseDto.ResponseDataDTO;
import com.xworkz.utils.CommonUtils;
import com.xworkz.utils.CustomeMailSender;
import com.xworkz.utils.PasswordGenerator;
import com.xworkz.utils.TimeConversion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentAdminServiceImpl implements DepartmentAdminService {

    @Autowired
    private DepartmentAdminRepository departmentAdminRepository;

    @Autowired
    private ComplaintRepository complaintRepository; // Assuming you have this repository

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CustomeMailSender mailSender;


    @Override
    @Transactional
    public String signin(RequestSigningDTO requestSigningDTO, Model model) {
        Optional<DepartmentAdminDTO> adminOpt = departmentAdminRepository.findByEmail(requestSigningDTO.getEmail());
        if (adminOpt.isPresent()) {
            DepartmentAdminDTO departmentAdminDTO = adminOpt.get();
            // Check password and other details
            if (departmentAdminDTO.getPassword().equals(requestSigningDTO.getPassword())) {
                // Update login count or other session-related info if needed
                departmentAdminDTO.setLoginCount(departmentAdminDTO.getLoginCount() + 1);
                departmentAdminRepository.update(departmentAdminDTO);

                model.addAttribute("departmentAdminData",departmentAdminDTO);
                return "DepartmentAdmin";
            }
        }

        model.addAttribute("infoError","Invalid Email or Password");

        return "SignIn";
    }

    @Override
    @Transactional
    public String signIn(RequestSigningDTO requestSigningDTO, Model model) {
        System.out.println("Department Admin Signing process is initiated with request signin dto " + requestSigningDTO);

        Optional<DepartmentAdminDTO> departmentAdminDTOOptional = departmentAdminRepository.findByEmail(requestSigningDTO.getEmail());

        if (departmentAdminDTOOptional.isPresent()) {
            DepartmentAdminDTO departmentAdminDTO = departmentAdminDTOOptional.get();

            if(departmentAdminDTO.isLock()){
                throw new InfoException(" Your account is Locked. Please Reset your password.");
            }

            if (departmentAdminDTO.getPassword().equals(requestSigningDTO.getPassword())) {
                if (departmentAdminDTO.getLoginCount() == 0 || departmentAdminDTO.getFailedAttempts() == 3) {

                    return "ResetPassword";
                }

                departmentAdminDTO.setFailedAttempts(0);

//                save updated counts to database
                departmentAdminRepository.update(departmentAdminDTO);
                model.addAttribute("departmentAdminData",departmentAdminDTO);

//                model.addAttribute("userDto", userDTO);
//
//                Optional<ImageDTO> imageDTO = imageService.findActiveImageByUserId(userDTO.getId());
//                if(imageDTO.isPresent()){
//                    model.addAttribute("imageData",imageDTO.get());
//                }

                return "DepartmentAdmin";
            } else {

//                3 time wrong attempts allowed within the 1 hour after that it will be allowed
//                3 attempts if previous attempts are within 3 then only allowed more 3 attempts in next 1 hour

                long duration = TimeConversion.getDuration(departmentAdminDTO.getFailedAttemptsDateTime());
                System.out.println("trying to check the attempts");
                if (duration < CommonUtils.DEPARTMENT_ADMIN_NUMBER_OF_ATTEMPTS_ALLOWED_IN_HOURS && departmentAdminDTO.getFailedAttempts() <= 3) {
                    System.out.println("under valid attempts : " + departmentAdminDTO.getFailedAttempts());
                    if (departmentAdminDTO.getFailedAttempts() == 0) {
                        departmentAdminDTO.setFailedAttemptsDateTime(LocalDateTime.now());
                    }
                    departmentAdminDTO.setFailedAttempts(departmentAdminDTO.getFailedAttempts() + 1);

                    if(departmentAdminDTO.getFailedAttempts() == 3){
                        departmentAdminDTO.setLock(true);
                    }

//                    save updated counts to database.
                    departmentAdminRepository.update(departmentAdminDTO);

                } else if (duration >= CommonUtils.DEPARTMENT_ADMIN_NUMBER_OF_ATTEMPTS_ALLOWED_IN_HOURS) {
                    System.out.println("checking under specified duration attempts are valid or not attempt = "+departmentAdminDTO.getFailedAttempts());
                    if (departmentAdminDTO.getFailedAttempts() < 3) {
                        System.out.println("attempts are valid under 3 after an hours so resting the time and count");
                        departmentAdminDTO.setFailedAttemptsDateTime(LocalDateTime.now());
                        departmentAdminDTO.setFailedAttempts(1);

//                    save updated counts to database.
                        departmentAdminRepository.update(departmentAdminDTO);

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
    @Transactional
    public Boolean createEmployee() {
        // Logic to create an employee
        // For example, it could involve saving a new `DepartmentAdminDTO`
        // This is a placeholder implementation
        DepartmentAdminDTO newEmployee = new DepartmentAdminDTO();
        // Set necessary properties of newEmployee
        return departmentAdminRepository.save(newEmployee);
    }

    @Override
    @Transactional
    public Boolean assignEmployee() {
        // Logic to assign an employee
        // This could involve updating some details of an existing `DepartmentAdminDTO`
        // This is a placeholder implementation
        Optional<DepartmentAdminDTO> adminOpt = departmentAdminRepository.findById(1L); // Example ID
        if (adminOpt.isPresent()) {
            DepartmentAdminDTO admin = adminOpt.get();
            // Update details to assign the employee
            return departmentAdminRepository.update(admin);
        }
        return false;
    }

    @Override
    public DTOListPage<ComplaintDTO> viewComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO, Integer offset, Integer pageSize, Model model) {

        ComplaintDTO complaintDTO = modelMapper.map(requestFilterComplaintDTO,ComplaintDTO.class);
        DTOListPage<ComplaintDTO> complaintDTOList = complaintRepository.searchAllComplaintsForAdmin(complaintDTO,offset,pageSize);

        return complaintDTOList;
    }

    @Override
    @Transactional
    public Boolean validateAndSave(RequestDepartmentAdminDTO requestDepartmentAdminDTO, Model model) {
        System.out.println("Department Admin validate and save process is initiated with dto "+requestDepartmentAdminDTO);

        try {

            boolean check = departmentAdminRepository.checkEmail(requestDepartmentAdminDTO.getEmail());

            if (check) {
                throw new InfoException("Email already exist");
            }

            check = departmentAdminRepository.checkMobile(requestDepartmentAdminDTO.getMobile());

            if (check) {
                throw new InfoException("Mobile number already exist");
            }

//        DepartmentAdminDTO departmentAdminDTO = new DepartmentAdminDTO();
//
//        departmentAdminDTO.setFname(requestDepartmentAdminDTO.getFname());
//        departmentAdminDTO.setLname(requestDepartmentAdminDTO.getLname());
//        departmentAdminDTO.setEmail(requestDepartmentAdminDTO.getEmail());
//        departmentAdminDTO.setMobile(requestDepartmentAdminDTO.getMobile());
//        departmentAdminDTO.setDepartmentId(requestDepartmentAdminDTO.getDepartmentId());

            DepartmentAdminDTO departmentAdminDTO = modelMapper.map(requestDepartmentAdminDTO, DepartmentAdminDTO.class);
            departmentAdminDTO.setId(null);
            String password = PasswordGenerator.generatePassword();
            departmentAdminDTO.setPassword(password);
            departmentAdminDTO.setCreatedBy("Admin");
            departmentAdminDTO.setCreatedDate(LocalDateTime.now());

            System.out.println("saving data " + departmentAdminDTO);

            departmentAdminRepository.save(departmentAdminDTO);

            Optional<DepartmentDTO> departmentDTO = departmentRepository.findById(departmentAdminDTO.getDepartmentId());

            if (!departmentDTO.isPresent()){
                throw new InfoException("Department not present");
            }

            mailSender.sendDepartmentAdminMail(departmentAdminDTO.getEmail(),password,departmentDTO.get().getDepartmentName());

            return true;
        }
        catch (InfoException infoException){
            throw new InfoException(infoException.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public ResponseDTO checkMobile(String mobile) {
        boolean check = departmentAdminRepository.checkMobile(mobile);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("200");
        responseDTO.setData(new ResponseDataDTO(check));
        return responseDTO;
    }

    @Override
    public ResponseDTO checkMail(String mail) {
        boolean check = departmentAdminRepository.checkEmail(mail);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("200");
        responseDTO.setData(new ResponseDataDTO(check));
        return responseDTO;
    }


    @Override
    public DTOListPage<DepartmentAdminDTO> findAllDepartmentAdmin(Integer offset, Integer pageSize) {
        System.out.println("Fetching all department process is initiated. "+offset+" "+pageSize);
        return departmentAdminRepository.findAllByPagination(offset,pageSize);
    }

    @Override
    @Transactional
    public String validateAndSetForgotPassword(RequestForgotPasswordDTO requestForgotPasswordDTO, Model model) {
        System.out.println("Department Admin forgot password process is initiated with request email dto " + requestForgotPasswordDTO);

        Optional<DepartmentAdminDTO> adminDTOList = departmentAdminRepository.findByEmail(requestForgotPasswordDTO.getEmail());

        if (adminDTOList.isPresent()) {
            DepartmentAdminDTO departmentAdminDTO = adminDTOList.get();
            String generatePassword = PasswordGenerator.generatePassword();
            departmentAdminDTO.setFailedAttempts(3);
            departmentAdminDTO.setPassword(generatePassword);
            departmentAdminDTO.setLock(false);
            departmentAdminDTO.setResetPasswordDateTime(LocalDateTime.now());

            departmentAdminRepository.update(departmentAdminDTO);
            mailSender.sendResetPasswordMailToDepartmentAdmin(departmentAdminDTO.getEmail(),departmentAdminDTO.getPassword());
            model.addAttribute("successMessage","We have sent an email containing a temporary password to your registered email address. You can use this temporary password to log in and reset your password. Please ensure you use the temporary password within 10 minutes to complete the reset process. ");
            return "ForgotPassword";
        }

        throw new InfoException("Invalid Email!");
    }

    @Override
    @Transactional
    public Boolean validateAndResetPassword(RequestResetPasswordDTO requestResetPasswordDTO) {
        System.out.println("Department Admin reset password process is initiated with request password dto " + requestResetPasswordDTO);

        Optional<DepartmentAdminDTO> departmentAdminDTOOptional = departmentAdminRepository.findByEmail(requestResetPasswordDTO.getEmail());

        if (!requestResetPasswordDTO.getNewPassword().equals(requestResetPasswordDTO.getConfirmPassword())) {
            throw new InfoException("New Password and Conform Password must have same value.");
        }

        if (departmentAdminDTOOptional.isPresent()) {
            DepartmentAdminDTO departmentAdminDTO = departmentAdminDTOOptional.get();

            if(departmentAdminDTO.isLock()){
                throw new InfoException("Please follow forgot password process in order to unlock and reset an account.");
            }

            if(departmentAdminDTO.getLoginCount() == 0 || departmentAdminDTO.getFailedAttempts() == 3){
                if (departmentAdminDTO.getPassword().equals(requestResetPasswordDTO.getPassword())) {

                    long minutes = TimeConversion.getDurationInMinutes(departmentAdminDTO.getResetPasswordDateTime());

                    if(minutes > CommonUtils.DEPARTMENT_ADMIN_PASSWORD_RESET_DURATION){
                        throw new InfoException("The temporary password has expired. Please request a new password reset to proceed.");
                    }

                    departmentAdminDTO.setPassword(requestResetPasswordDTO.getNewPassword());
                    departmentAdminDTO.setFailedAttempts(0);
                    if(departmentAdminDTO.getLoginCount() == 0) departmentAdminDTO.setLoginCount(1);

                    return departmentAdminRepository.update(departmentAdminDTO);
                }
            }else {
                throw new InfoException("Not allowed to reset the password");
            }

        }

        throw new InfoException("Invalid Email or Password");
    }

    @Override
    public ComplaintDTO searchComplaint(int complaintId, Long deptId) {
        ComplaintDTO complaintDTO = new ComplaintDTO();
        complaintDTO.setId((long) complaintId);
        complaintDTO.setDeptId(deptId);
        Optional<List<ComplaintDTO>> optionalComplaintDTOList = complaintRepository.searchAllComplaintsForAdmin(complaintDTO);

        if(!optionalComplaintDTOList.isPresent() && optionalComplaintDTOList.get().isEmpty()){
            throw new InfoException("Complaint Not Found");
        }

        return optionalComplaintDTOList.get().get(0);
    }
}
