package com.xworkz.service;

import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.ComplaintDTO;
import com.xworkz.entity.DepartmentAdminDTO;
import com.xworkz.entity.DepartmentDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.repository.ComplaintRepository; // Assuming you have this repository
import com.xworkz.repository.DepartmentAdminRepository;
import com.xworkz.repository.DepartmentRepository;
import com.xworkz.requestDto.RequestDepartmentAdminDTO;
import com.xworkz.requestDto.RequestFilterComplaintDTO;
import com.xworkz.requestDto.RequestSigningDTO;
import com.xworkz.responseDto.ResponseDTO;
import com.xworkz.responseDto.ResponseDataDTO;
import com.xworkz.utils.CustomeMailSender;
import com.xworkz.utils.PasswordGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
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
}
