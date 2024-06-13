package com.xworkz.service;

import com.xworkz.dto.UserDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.repository.UserRepository;
import com.xworkz.requestDto.RequestResetPasswordDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.requestDto.RequestSignupDTO;
import com.xworkz.utils.PasswordGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Boolean validateAndSave(RequestSignupDTO signupDTO) {
        System.out.println("User Service process is initiated using DTO: "+signupDTO);

        Optional<List<UserDTO>> userDTOList = userRepository.findByUserMail(signupDTO.getEmail());

        if(userDTOList.isPresent() && !userDTOList.get().isEmpty()){
            System.out.println(userDTOList.get());
            throw new InfoException("Email already exist");
        }

        Optional<List<UserDTO>> userDTOList1 = userRepository.findByUserMobile(signupDTO.getMobile());

        if(userDTOList1.isPresent() && !userDTOList1.get().isEmpty()){
            System.out.println(userDTOList1.get());
            throw new InfoException("Mobile number already exist");
        }

        UserDTO userDTO = modelMapper.map(signupDTO,UserDTO.class);
        userDTO.setCreatedBy(userDTO.getFname()+" "+userDTO.getLname());
        userDTO.setCreatedDate(LocalDateTime.now());
        userDTO.setPassword(PasswordGenerator.generatePassword());
        System.out.println(userDTO);
        Boolean result= userRepository.save(userDTO);
        sendMail(userDTO.getEmail(),userDTO.getPassword());

        return result;
    }

    @Override
    public String signin(RequestSigningDTO requestSigningDTO, Model model) {
        System.out.println("User Sigin process is initiated with request signin dto "+requestSigningDTO);

        Optional<List<UserDTO>> userDTOList = userRepository.findByUserMail(requestSigningDTO.getEmail());

        if(userDTOList.isPresent() && !userDTOList.get().isEmpty()) {
            UserDTO userDTO = userDTOList.get().get(0);
            if(userDTO.getPassword().equals(requestSigningDTO.getPassword())){
                if(userDTO.getLoginCount() == 0){
                    return "ResetPassword";
                }
                model.addAttribute("userDto",userDTO);
                return "User";
            }
        }

        throw new InfoException("Invalid Email or Password");
    }

    @Override
    public boolean validateAndResetPassword(RequestResetPasswordDTO requestResetPasswordDTO) {
        System.out.println("User reset password process is initiated with request password dto "+requestResetPasswordDTO);

        Optional<List<UserDTO>> userDTOList = userRepository.findByUserMail(requestResetPasswordDTO.getEmail());

        if(!requestResetPasswordDTO.getNewPassword().equals(requestResetPasswordDTO.getConfirmPassword())){
            throw new InfoException("New Password and Conform Password must have same value.");
        }

        if(userDTOList.isPresent() && !userDTOList.get().isEmpty()){
            UserDTO userDTO=userDTOList.get().get(0);
            if(userDTO.getPassword().equals(requestResetPasswordDTO.getPassword())){
                return userRepository.updatePassword(userDTO,requestResetPasswordDTO.getNewPassword());
            }
        }

        throw new InfoException("Invalid Email or Password");
    }


    private void sendMail(String mail, String password){
        System.out.println("Mail has successfully sent to " + mail);

        String subject = "Welcome to Our Service!";
        String text = String.format(
                "Hello,\n\n" +
                        "Welcome to our service! Your account has been successfully created.\n\n" +
                        "Here are your account details:\n" +
                        "Email: %s\n" +
                        "Password: %s\n\n" +
                        "Please make sure to change your password after logging in for the first time for security purposes.\n\n" +
                        "Thank you for joining us!\n" +
                        "Best regards,\n" +
                        "The Team",
                mail, password
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
