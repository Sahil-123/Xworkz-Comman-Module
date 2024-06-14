package com.xworkz.service;

import com.xworkz.dto.AdminDTO;
import com.xworkz.repository.AdminRepository;
import com.xworkz.requestDto.RequestSigningDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public String validateAdmin(RequestSigningDTO requestSigningDTO, Model model) {

        Optional<List<AdminDTO>> adminDTOList = adminRepository.findByEmail(requestSigningDTO.getEmail());
        if(adminDTOList.isPresent()){
            AdminDTO adminDTO = adminDTOList.get().get(0);
            System.out.println("admin "+ adminDTO);
            if(adminDTO.getPassword().equals(requestSigningDTO.getPassword())){
                model.addAttribute("adminDto",adminDTO);
                if(adminDTO.getLoginCount() == 0){

                    return "ResetPassword";
                }

                return "Admin";
            }
        }

        model.addAttribute("infoError","Invalid Email or Password");
        return "Signin";
    }
}
