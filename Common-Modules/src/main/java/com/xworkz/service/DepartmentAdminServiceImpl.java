package com.xworkz.service;

import com.xworkz.entity.ComplaintDTO;
import com.xworkz.entity.DepartmentAdminDTO;
import com.xworkz.repository.ComplaintRepository; // Assuming you have this repository
import com.xworkz.repository.DepartmentAdminRepository;
import com.xworkz.requestDto.RequestFilterComplaintDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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

    @Override
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
    public Boolean createEmployee() {
        // Logic to create an employee
        // For example, it could involve saving a new `DepartmentAdminDTO`
        // This is a placeholder implementation
        DepartmentAdminDTO newEmployee = new DepartmentAdminDTO();
        // Set necessary properties of newEmployee
        return departmentAdminRepository.save(newEmployee);
    }

    @Override
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
    public Optional<List<ComplaintDTO>> viewComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO, Model model) {

        ComplaintDTO complaintDTO = modelMapper.map(requestFilterComplaintDTO,ComplaintDTO.class);
        Optional<List<ComplaintDTO>> complaintDTOList = complaintRepository.searchAllComplaintsForAdmin(complaintDTO);

        return complaintDTOList;
    }
}
