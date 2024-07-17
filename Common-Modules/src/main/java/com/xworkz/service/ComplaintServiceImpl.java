package com.xworkz.service;

import com.xworkz.dto.ComplaintDTO;
import com.xworkz.dto.EmployeeDTO;
import com.xworkz.dto.UserDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.repository.ComplaintRepository;
import com.xworkz.requestDto.*;
//import com.xworkz.service.ComplaintService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public List<ComplaintDTO> findAllComplaints() {
        return complaintRepository.findAll().orElse(null);
    }

    @Override
    public Optional<ComplaintDTO> findComplaintById(Long id) {
        return complaintRepository.findById(id);
    }

    @Override
    public ComplaintDTO saveComplaint(RequestComplaintDTO requestComplaintDTO,UserDTO userDTO) {
        System.out.println("Complaint Service save complaint process is initiated using dto." + requestComplaintDTO);

//        UserDTO userDTO = modelMapper.map(signupDTO, UserDTO.class);
        ComplaintDTO complaintDTO = modelMapper.map(requestComplaintDTO,ComplaintDTO.class);
        complaintDTO.setCreatedBy(userDTO.getFname()+" "+userDTO.getLname());
        complaintDTO.setCreatedDate(LocalDateTime.now());
        complaintDTO.setUserId(userDTO.getId());

        if (complaintRepository.save(complaintDTO)) {
            return complaintDTO;
        }
        return null;
    }

    @Override
    public void deleteComplaint(Long id) {
        complaintRepository.deleteById(id);
    }

    @Override
    public ComplaintDTO updateComplaint(ComplaintDTO complaint) {
        if (complaintRepository.update(complaint)) {
            return complaint;
        }
        return null;
    }

    @Override
    public Optional<List<ComplaintDTO>> searchComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO) {
        System.out.println("Search Complaint "+requestFilterComplaintDTO);
        ComplaintDTO complaintDTO = modelMapper.map(requestFilterComplaintDTO,ComplaintDTO.class);
        return complaintRepository.searchComplaints(complaintDTO);
    }

    @Override
    public Optional<List<ComplaintDTO>> searchComplaintsForAdmin(RequestFilterComplaintDTO requestFilterComplaintDTO) {
        System.out.println("Search Complaint For admin in service "+requestFilterComplaintDTO);
        ComplaintDTO complaintDTO = modelMapper.map(requestFilterComplaintDTO,ComplaintDTO.class);
        return complaintRepository.searchAllComplaintsForAdmin(complaintDTO);
    }

    @Override
    public Boolean updateComplaint(RequestUpdateComplaintDTO requestUpdateComplaintDTO) {
        System.out.println("service complaint update processs "+requestUpdateComplaintDTO);
        return complaintRepository.updateComplaint(requestUpdateComplaintDTO);
    }

    @Override
    public Boolean updateComplaintForAdmin(RequestUpdateComplaintByAdminDTO requestUpdateComplaintByAdminDTO) {
        System.out.println("service complaint update for admin processes "+requestUpdateComplaintByAdminDTO);

        ComplaintDTO complaintDTO = complaintRepository.findById(requestUpdateComplaintByAdminDTO.getComplaintId()).get();
        complaintDTO.setDeptId(requestUpdateComplaintByAdminDTO.getDepartment());
        complaintDTO.setStatus(requestUpdateComplaintByAdminDTO.getStatus());
        complaintDTO.setEmpId(-1L);

        if(!complaintRepository.update(complaintDTO)){
            throw new InfoException("Something is wrong complaint with id = "+requestUpdateComplaintByAdminDTO.getComplaintId()+" not updated");
        }

        return true;
    }

    @Override
    public Boolean updateComplaintForDepartmentAdmin(RequestUpdateDepartmentComplaintByAdminDTO requestUpdateDepartmentComplaintByAdminDTO) {
        System.out.println("service complaint update for department admin processes "+requestUpdateDepartmentComplaintByAdminDTO);

        ComplaintDTO complaintDTO = complaintRepository.findById(requestUpdateDepartmentComplaintByAdminDTO.getComplaintId()).get();
        complaintDTO.setEmpId(requestUpdateDepartmentComplaintByAdminDTO.getEmployeeId());
        complaintDTO.setStatus(requestUpdateDepartmentComplaintByAdminDTO.getStatus());

        if(!complaintRepository.update(complaintDTO)){
            throw new InfoException("Something is wrong complaint with id = "+requestUpdateDepartmentComplaintByAdminDTO.getComplaintId()+" not updated");
        }

        return true;
    }

    @Override
    public Optional<List<ComplaintDTO>> searchNotResolvedComplaintsForEmployee(RequestFilterComplaintDTO requestFilterComplaintDTO, EmployeeDTO employeeDTO) {
        System.out.println("Search Not Resolved Complaint For Employee in Complaint service "+requestFilterComplaintDTO);
        ComplaintDTO complaintDTO = modelMapper.map(requestFilterComplaintDTO,ComplaintDTO.class);
        addEmployeeDetails(employeeDTO, complaintDTO);

        return complaintRepository.searchAllComplaintsForNotResolved(complaintDTO);
    }

    @Override
    public Optional<List<ComplaintDTO>> searchResolvedComplaintsForEmployee(RequestFilterComplaintDTO requestFilterComplaintDTO, EmployeeDTO employeeDTO) {
        System.out.println("Search Resolved Complaint For Employee in Complaint service "+requestFilterComplaintDTO);
        ComplaintDTO complaintDTO = modelMapper.map(requestFilterComplaintDTO,ComplaintDTO.class);
        addEmployeeDetails(employeeDTO, complaintDTO);

        return complaintRepository.searchAllComplaintsForResolved(complaintDTO);
    }

    private static void addEmployeeDetails(EmployeeDTO employeeDTO, ComplaintDTO complaintDTO) {
        complaintDTO.setEmpId(employeeDTO.getId());
        complaintDTO.setDeptId(employeeDTO.getDepartmentId());
    }
}
