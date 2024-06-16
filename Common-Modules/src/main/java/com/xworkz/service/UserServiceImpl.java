package com.xworkz.service;

import com.xworkz.dto.UserDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.repository.UserRepository;
import com.xworkz.requestDto.RequestForgotPasswordDTO;
import com.xworkz.requestDto.RequestResetPasswordDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.requestDto.RequestSignupDTO;
import com.xworkz.utils.CustomeMailSender;
import com.xworkz.utils.PasswordGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomeMailSender mailSender;



    @Override
    public Boolean validateAndSave(RequestSignupDTO signupDTO) {
        System.out.println("User Service process is initiated using DTO: " + signupDTO);

        Optional<List<UserDTO>> userDTOList = userRepository.findByUserMail(signupDTO.getEmail());

        if (userDTOList.isPresent() && !userDTOList.get().isEmpty()) {
            System.out.println(userDTOList.get());
            throw new InfoException("Email already exist");
        }

        Optional<List<UserDTO>> userDTOList1 = userRepository.findByUserMobile(signupDTO.getMobile());

        if (userDTOList1.isPresent() && !userDTOList1.get().isEmpty()) {
            System.out.println(userDTOList1.get());
            throw new InfoException("Mobile number already exist");
        }

        UserDTO userDTO = modelMapper.map(signupDTO, UserDTO.class);
        userDTO.setCreatedBy(userDTO.getFname() + " " + userDTO.getLname());
        userDTO.setCreatedDate(LocalDateTime.now());
        userDTO.setPassword(PasswordGenerator.generatePassword());
        System.out.println(userDTO);
        Boolean result = userRepository.save(userDTO);
        mailSender.sendSignupMail(userDTO.getEmail(), userDTO.getPassword());

        return result;
    }

    @Override
    public String signin(RequestSigningDTO requestSigningDTO, Model model) {
        System.out.println("User Sigin process is initiated with request signin dto " + requestSigningDTO);

        Optional<List<UserDTO>> userDTOList = userRepository.findByUserMail(requestSigningDTO.getEmail());

        if (userDTOList.isPresent() && !userDTOList.get().isEmpty()) {
            UserDTO userDTO = userDTOList.get().get(0);

            if(userDTO.isLock()){
                throw new InfoException(" Your account is Locked. Please Reset your password.");
            }

            if (userDTO.getPassword().equals(requestSigningDTO.getPassword())) {
                if (userDTO.getLoginCount() == 0 || userDTO.getFailedAttemptsCount() == 3) {
                    return "ResetPassword";
                }
                userDTO.setFailedAttemptsCount(0);

//                save updated counts to database
                userRepository.updateByDto(userDTO);

                model.addAttribute("userDto", userDTO);
                return "User";
            } else {

//                3 time wrong attempts allowed within the 1 hour after that it will be allowed
//                3 attempts if previous attempts are within 3 then only allowed more 3 attempts in next 1 hour

                long duration = getDuration(userDTO.getFailedAttemptDateTime());
                System.out.println("trying to check the attempts");
                if (duration < 1L && userDTO.getFailedAttemptsCount() < 3) {
                    System.out.println("under valid attempts : " + userDTO.getFailedAttemptsCount());
                    if (userDTO.getFailedAttemptsCount() == 0) {
                        userDTO.setFailedAttemptDateTime(LocalDateTime.now());
                    }
                    userDTO.setFailedAttemptsCount(userDTO.getFailedAttemptsCount() + 1);
                    userDTO.setLock(true);

//                    save updated counts to database.
                    userRepository.updateByDto(userDTO);

                } else if (duration >= 1L) {
                    System.out.println("checking under specified duration attempts are valid or not attempt = "+userDTO.getFailedAttemptsCount());
                    if (userDTO.getFailedAttemptsCount() < 3) {
                        System.out.println("attempts are valid under 3 after an hours so resting the time and count");
                        userDTO.setFailedAttemptDateTime(LocalDateTime.now());
                        userDTO.setFailedAttemptsCount(1);

//                    save updated counts to database.
                        userRepository.updateByDto(userDTO);

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
    public boolean validateAndResetPassword(RequestResetPasswordDTO requestResetPasswordDTO) {
        System.out.println("User reset password process is initiated with request password dto " + requestResetPasswordDTO);

        Optional<List<UserDTO>> userDTOList = userRepository.findByUserMail(requestResetPasswordDTO.getEmail());

        if (!requestResetPasswordDTO.getNewPassword().equals(requestResetPasswordDTO.getConfirmPassword())) {
            throw new InfoException("New Password and Conform Password must have same value.");
        }

        if (userDTOList.isPresent() && !userDTOList.get().isEmpty()) {
            UserDTO userDTO = userDTOList.get().get(0);

            if(userDTO.isLock()){
                throw new InfoException("Please follow forgot password process in order to unlock and reset an account.");
            }

            if(userDTO.getLoginCount() == 0 || userDTO.getFailedAttemptsCount() == 3){
                if (userDTO.getPassword().equals(requestResetPasswordDTO.getPassword())) {
                    userDTO.setPassword(requestResetPasswordDTO.getNewPassword());
                    userDTO.setFailedAttemptsCount(0);

                    if(userDTO.getLoginCount() == 0 ) userDTO.setLoginCount(1);

                    return userRepository.updateByDto(userDTO);
                }
            }else {
                throw new InfoException("Not allowed to reset the password");
            }

        }

        throw new InfoException("Invalid Email or Password");
    }

    @Override
    public String validateAndSetForgotPassword(RequestForgotPasswordDTO requestForgotPasswordDTO, Model model) {
        System.out.println("User forgot password process is initiated with request email dto " + requestForgotPasswordDTO);

        Optional<List<UserDTO>> userDTOList = userRepository.findByUserMail(requestForgotPasswordDTO.getEmail());

        if (userDTOList.isPresent() && !userDTOList.get().isEmpty()) {
            UserDTO userDTO = userDTOList.get().get(0);
            String generatePassword = PasswordGenerator.generatePassword();
            userDTO.setPassword(generatePassword);
            userDTO.setFailedAttemptsCount(3);
            userDTO.setLock(false);
            userRepository.updateByDto(userDTO);
            mailSender.sendResetPasswordMail(userDTO.getEmail(),userDTO.getPassword());
            model.addAttribute("successMessage","We have sent an email containing a temporary password to your registered email address. You can use this temporary password to log in and reset your password.");
            return "ForgotPassword";
        }

        throw new InfoException("Invalid Email!");
    }

    @Override
    public Optional<List<UserDTO>> getAllUser() {
        System.out.println("Running User service impl get all user method to find all the users.");
        return userRepository.getAllUsers();
    }
}
