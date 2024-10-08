package com.xworkz.controller;

import com.xworkz.entity.ComplaintDTO;
import com.xworkz.entity.DepartmentAdminDTO;
import com.xworkz.entity.ImageDTO;
import com.xworkz.entity.UserDTO;
import com.xworkz.requestDto.UserProfileDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.requestDto.RequestForgotPasswordDTO;
import com.xworkz.requestDto.RequestResetPasswordDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.service.ComplaintService;
import com.xworkz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes({"userData","imageData","imageFilePath"})
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/signin")
    public String signin(@Valid RequestSigningDTO requestSigningDTO, BindingResult bindingResult, Model model) {
        System.out.println("Signin Form processing with dto " + requestSigningDTO);
        model.addAttribute("dto", requestSigningDTO);

        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("errors", bindingResult.getAllErrors());
                return "SignIn";
            }

            String result = userService.signin(requestSigningDTO, model);
            return result;
        } catch (InfoException e) {
            System.out.println(e.getMessage());
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "SignIn";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@Valid RequestResetPasswordDTO requestResetPasswordDTO, BindingResult bindingResult, Model model) {
        System.out.println("Reset Form processing with dto " + requestResetPasswordDTO);

        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("dto", requestResetPasswordDTO);
                model.addAttribute("errors", bindingResult.getAllErrors());
                return "ResetPassword";
            }

            userService.validateAndResetPassword(requestResetPasswordDTO);
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



    @PostMapping("/forgotPassword")
    public String forgotPassword(@Valid RequestForgotPasswordDTO requestForgotPasswordDTO, BindingResult bindingResult, Model model) {
        System.out.println("User forgot password process is initiated with dto "+requestForgotPasswordDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "ForgotPassword";
        }

        try {
            return userService.validateAndSetForgotPassword(requestForgotPasswordDTO, model);
        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return "ForgotPassword";
    }

    @PostMapping("/editProfile")
    public String editProfile(UserProfileDTO userProfile,BindingResult bindingResult,Model model) {
        System.out.println("Edit profile process is initiated."+ userProfile);

        if(bindingResult.hasErrors()){
            model.addAttribute("errors",bindingResult.getAllErrors());
            return "user/UserEditProfilePage";
        }

        try {
            userService.editProfile(userProfile, model);
        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }


        model.addAttribute("successMessage","Profile update is completed");
        return "user/UserEditProfilePage";
    }


    @PostMapping("/upload")
    public String upload(@RequestParam("profilePicture") MultipartFile multipartFile, Model model) {
        System.out.println("Edit profile process is initiated.");


        try {
            if(multipartFile.isEmpty()){
                System.out.println("file is empty");
            }

            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get("D:\\Xworkz-Comman-Module\\uploadedImages\\" + multipartFile.getOriginalFilename());
            Files.write(path, multipartFile.getBytes());

            System.out.println("File upload successful.");

        } catch (InfoException e) {
            model.addAttribute("infoError", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            model.addAttribute("infoError", "Something went wrong please try again later.");

            e.printStackTrace();
            return "user/UserEditProfilePage";
        }

        model.addAttribute("successMessage","Profile update is completed");
        return "user/UserEditProfilePage";
    }

    @GetMapping("/profileImages")
    public void getImage(ServletResponse response, Model model) throws IOException {
        System.out.println("Image fetching process initiated");
        ImageDTO imageDTO = (ImageDTO) model.getAttribute("imageData");
        if (imageDTO == null) {
            throw new IllegalArgumentException("Image data not found in model");
        }

        // Example file path, replace with your actual image file path
        Path path = Paths.get("D:\\Xworkz-Comman-Module\\uploadedImages\\"+imageDTO.getImageName());

        // Set the content type and buffer size
        response.setContentType(imageDTO.getImageType());
        response.setBufferSize(imageDTO.getImageSize());

        // Write the file to the response output stream
        try (OutputStream outputStream = response.getOutputStream()) {
            byte[] byteData = Files.readAllBytes(path);
            outputStream.write(byteData);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("index")
    public String logout(SessionStatus sessionStatus){
        sessionStatus.setComplete();

        return "index";
    }

    @GetMapping("/home")
    public String getUserHome(Model model){
        model.addAttribute("link","user");
        return "User";
    }

    @GetMapping("raiseComplaint")
    public String raiseComplaint(Model model){
        model.addAttribute("link","raiseComplaint");
        return "User";
    }



}
