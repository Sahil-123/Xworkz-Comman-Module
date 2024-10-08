package com.xworkz.service;

import com.xworkz.entity.AdminDTO;
import com.xworkz.entity.ComplaintDTO;
import com.xworkz.enums.Roles;
import com.xworkz.exceptions.InfoException;
import com.xworkz.repository.AdminRepository;
import com.xworkz.repository.ComplaintRepository;
import com.xworkz.requestDto.RequestForgotPasswordDTO;
import com.xworkz.requestDto.RequestResetPasswordDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.utils.CustomeMailSender;
import com.xworkz.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomeMailSender mailSender;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Override
    @Transactional
    public String signin(RequestSigningDTO requestSigningDTO, Model model) {

        Optional<List<AdminDTO>> adminDTOList = adminRepository.findByEmail(requestSigningDTO.getEmail());
        if(adminDTOList.isPresent()){
            AdminDTO adminDTO = adminDTOList.get().get(0);

            if(adminDTO.isLock()){
                throw new InfoException(" Your account is Locked. Please Reset your password.");
            }

            System.out.println("admin "+ adminDTO);
            if(adminDTO.getPassword().equals(requestSigningDTO.getPassword())){
                model.addAttribute("adminDto",adminDTO);
                if(adminDTO.getLoginCount() == 0 || adminDTO.getFailedAttemptsCount() == 3){

                    return "ResetPassword";
                }

                adminDTO.setFailedAttemptsCount(0);

//                save updated counts to database
                adminRepository.updateByDto(adminDTO);
                model.addAttribute("adminData",adminDTO);
                System.out.println("====================");
                System.out.println(model.getAttribute("adminData"));
//
////                Adding authentication in spring security context
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                        new UsernamePasswordAuthenticationToken(adminDTO.getEmail(),adminDTO.getPassword());
//                usernamePasswordAuthenticationToken.setDetails(Roles.ADMIN);
//                usernamePasswordAuthenticationToken.setAuthenticated(true);
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

//                List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(Roles.ADMIN.toString()));
//
//                // Create the UsernamePasswordAuthenticationToken with email, password, and authorities
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(adminDTO.getEmail(), adminDTO.getPassword(), authorities);
//
//                // Set the Authentication in the SecurityContext
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                return "Admin";
            }else {

//                3 time wrong attempts allowed within the 1 hour after that it will be allowed
//                3 attempts if previous attempts are within 3 then only allowed more 3 attempts in next 1 hour

                long duration = getDuration(adminDTO.getFailedAttemptDateTime());
                System.out.println("trying to check the attempts");
                if (duration < 1L && adminDTO.getFailedAttemptsCount() < 3) {
                    System.out.println("under valid attempts : " + adminDTO.getFailedAttemptsCount());
                    if (adminDTO.getFailedAttemptsCount() == 0) {
                        adminDTO.setFailedAttemptDateTime(LocalDateTime.now());
                    }
                    adminDTO.setFailedAttemptsCount(adminDTO.getFailedAttemptsCount() + 1);
                    adminDTO.setLock(true);

//                    save updated counts to database.
                    adminRepository.updateByDto(adminDTO);

                } else if (duration >= 1L) {
                    System.out.println("checking under specified duration attempts are valid or not attempt = "+adminDTO.getFailedAttemptsCount());
                    if (adminDTO.getFailedAttemptsCount() < 3) {
                        System.out.println("attempts are valid under 3 after an hours so resting the time and count");
                        adminDTO.setFailedAttemptDateTime(LocalDateTime.now());
                        adminDTO.setFailedAttemptsCount(1);

//                    save updated counts to database.
                        adminRepository.updateByDto(adminDTO);

                    }else{
                        throw new InfoException("Your have exceeded login attempts. Your account is Locked. Please Reset your password.");
                    }
                } else {
                    throw new InfoException("Your have exceeded login attempts. Your account is Locked. Please Reset your password.");
                }
            }
        }

        model.addAttribute("infoError","Invalid Email or Password");
        return "SignIn";
    }

    private Long getDuration(LocalDateTime startDateTime) {
        LocalDateTime endDateTime = LocalDateTime.now();

        if (startDateTime == null) return 0L;

        // Calculate the duration between the two LocalDateTime instances
        Duration duration = Duration.between(startDateTime, endDateTime);

        // Get the number of hours between the two LocalDateTime instances
        long hours = duration.toHours();

        System.out.println("Number of hours between the two dates: " + hours);
        System.out.println(LocalDateTime.now().minusHours(1L).minusMinutes(30));

        return hours;
    }

    @Override
    @Transactional
    public String validateAndSetForgotPassword(RequestForgotPasswordDTO requestForgotPasswordDTO,Model model) {
        System.out.println("Admin forgot password process is initiated with request email dto " + requestForgotPasswordDTO);

        Optional<List<AdminDTO>> adminDTOList = adminRepository.findByEmail(requestForgotPasswordDTO.getEmail());

        if (adminDTOList.isPresent() && !adminDTOList.get().isEmpty()) {
            AdminDTO adminDTO = adminDTOList.get().get(0);
            String generatePassword = PasswordGenerator.generatePassword();
            adminDTO.setFailedAttemptsCount(3);
            adminDTO.setPassword(generatePassword);
            adminDTO.setLock(false);
            adminRepository.updateByDto(adminDTO);
            mailSender.sendResetPasswordMail(adminDTO.getEmail(),adminDTO.getPassword());
            model.addAttribute("successMessage","We have sent an email containing a temporary password to your registered email address. You can use this temporary password to log in and reset your password.");
            return "ForgotPassword";
        }

        throw new InfoException("Invalid Email!");
    }


    @Override
    @Transactional
    public boolean validateAndResetPassword(RequestResetPasswordDTO requestResetPasswordDTO) {
        System.out.println("User reset password process is initiated with request password dto " + requestResetPasswordDTO);

        Optional<List<AdminDTO>> adminDTOList = adminRepository.findByEmail(requestResetPasswordDTO.getEmail());

        if (!requestResetPasswordDTO.getNewPassword().equals(requestResetPasswordDTO.getConfirmPassword())) {
            throw new InfoException("New Password and Conform Password must have same value.");
        }

        if (adminDTOList.isPresent() && !adminDTOList.get().isEmpty()) {
            AdminDTO adminDTO = adminDTOList.get().get(0);

            if(adminDTO.isLock()){
                throw new InfoException("Please follow forgot password process in order to unlock and reset an account.");
            }

            if(adminDTO.getLoginCount() == 0 || adminDTO.getFailedAttemptsCount() == 3){
                if (adminDTO.getPassword().equals(requestResetPasswordDTO.getPassword())) {
                    adminDTO.setPassword(requestResetPasswordDTO.getNewPassword());
                    adminDTO.setFailedAttemptsCount(0);
                    if(adminDTO.getLoginCount() == 0) adminDTO.setLoginCount(1);

                    return adminRepository.updateByDto(adminDTO);
                }
            }else {
                throw new InfoException("Not allowed to reset the password");
            }

        }

        throw new InfoException("Invalid Email or Password");
    }

    @Override
    @Transactional
    public ComplaintDTO searchComplaint(int complaintId) {
        Optional<ComplaintDTO> complaintDTOOptional = complaintRepository.findById((long)complaintId);
        if(!complaintDTOOptional.isPresent()){
            throw new InfoException("Complaint Not Found");
        }

        return complaintDTOOptional.get();
    }
}
