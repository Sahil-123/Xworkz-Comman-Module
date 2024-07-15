package com.xworkz.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class CustomeMailSender {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendSignupMail(String mail, String password) {
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

        javaMailSender.send(message);
    }

    public void sendResetPasswordMail(String mail, String newPassword) {
        System.out.println("Password reset mail has successfully sent to " + mail);

        String subject = "Password Reset Request";
        String text = String.format(
                "Hello,\n\n" +
                        "Your password has been successfully reset. You can now log in using the following new password:\n\n" +
                        "Email: %s\n" +
                        "New Password: %s\n\n" +
                        "Please make sure to change your password after logging in for the first time for security purposes.\n\n" +
                        "Thank you!\n" +
                        "Best regards,\n" +
                        "The Team",
                mail, newPassword
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }


    public void sendEmployeeRegisterMail(String mail, String password, String departmentName) {
        System.out.println("Mail has successfully sent to " + mail);

        String subject = "Welcome to the Department!";
        String text = String.format(
                "Hello,\n\n" +
                        "You have been registered as an admin for the %s department.\n\n" +
                        "Here are your login credentials:\n" +
                        "Email: %s\n" +
                        "Password: %s\n\n" +
                        "Please make sure to change your password after logging in for the first time for security purposes.\n\n" +
                        "Thank you for joining us!\n" +
                        "Best regards,\n" +
                        "The Team",
                departmentName, mail, password
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }


    public void sendComplaintResolutionMail(String mail, String otp) {
//        String otp = OTPGenerator.generateOTP();
        System.out.println("Generated OTP: " + otp);

        // The rest of your email sending code
        String subject = "OTP Verification for Issue Resolution";
        String text = String.format(
                "Hello,\n\n" +
                        "We have received your request to resolve an issue. To confirm that the issue has been resolved, please use the following OTP:\n\n" +
                        "OTP: %s\n\n" +
                        "Please enter this OTP to confirm the resolution of the issue.\n\n" +
                        "Thank you for your cooperation!\n" +
                        "Best regards,\n" +
                        "The Team",
                otp
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);

    }

}
