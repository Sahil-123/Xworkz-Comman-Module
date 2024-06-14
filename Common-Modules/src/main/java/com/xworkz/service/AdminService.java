package com.xworkz.service;

import com.xworkz.requestDto.RequestSigningDTO;
import org.springframework.ui.Model;

public interface AdminService {

    String validateAdmin(RequestSigningDTO requestSigningDTO, Model model);

}
