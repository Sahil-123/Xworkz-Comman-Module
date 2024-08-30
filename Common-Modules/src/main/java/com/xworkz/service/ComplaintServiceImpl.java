package com.xworkz.service;

import com.xworkz.dto.DTOListPage;
import com.xworkz.dto.NotificationList;
import com.xworkz.entity.ComplaintDTO;
import com.xworkz.entity.EmployeeDTO;
import com.xworkz.entity.UserDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.repository.ComplaintRepository;
import com.xworkz.requestDto.*;
//import com.xworkz.service.ComplaintService;
import com.xworkz.utils.CommonUtils;
import com.xworkz.utils.TimeConversion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
    @Transactional
    public ComplaintDTO saveComplaint(RequestComplaintDTO requestComplaintDTO,UserDTO userDTO) {
        System.out.println("Complaint Service save complaint process is initiated using dto." + requestComplaintDTO);

//        UserDTO userDTO = modelMapper.map(signupDTO, UserDTO.class);
        ComplaintDTO complaintDTO = modelMapper.map(requestComplaintDTO,ComplaintDTO.class);
        complaintDTO.setCreatedBy(userDTO.getFname()+" "+userDTO.getLname());
        complaintDTO.setCreatedDate(LocalDateTime.now());
        complaintDTO.setUserId(userDTO.getId());
//        complaintDTO

        if (complaintRepository.save(complaintDTO)) {
            return complaintDTO;
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteComplaint(Long id) {
        complaintRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ComplaintDTO updateComplaint(ComplaintDTO complaint) {
        if (complaintRepository.update(complaint)) {
            return complaint;
        }
        return null;
    }

    @Override
    @Transactional
    public ComplaintDTO searchComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO) {
        return null;
    }

    @Override
    public DTOListPage<ComplaintDTO> searchComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO, Integer offset, Integer pageSize) {
        System.out.println("Search Complaint "+requestFilterComplaintDTO);
        ComplaintDTO complaintDTO = modelMapper.map(requestFilterComplaintDTO,ComplaintDTO.class);
        return complaintRepository.searchComplaints(complaintDTO,offset,pageSize);
    }

    @Override
    public DTOListPage<ComplaintDTO> searchComplaintsForAdmin(RequestFilterComplaintDTO requestFilterComplaintDTO, Integer offset, Integer pageSize) {
        System.out.println("Search Complaint For admin in service "+requestFilterComplaintDTO);
        ComplaintDTO complaintDTO = modelMapper.map(requestFilterComplaintDTO,ComplaintDTO.class);
        return complaintRepository.searchAllComplaintsForAdmin(complaintDTO,offset,pageSize);
    }

    @Override
    @Transactional
    public Boolean updateComplaint(RequestUpdateComplaintDTO requestUpdateComplaintDTO) {
        System.out.println("service complaint update processs "+requestUpdateComplaintDTO);
        return complaintRepository.updateComplaint(requestUpdateComplaintDTO);
    }

    @Override
    @Transactional
    public Boolean updateComplaintForAdmin(RequestUpdateComplaintByAdminDTO requestUpdateComplaintByAdminDTO) {
        System.out.println("service complaint update for admin processes "+requestUpdateComplaintByAdminDTO);

        ComplaintDTO complaintDTO = complaintRepository.findById(requestUpdateComplaintByAdminDTO.getComplaintId()).get();
        complaintDTO.setDeptId(requestUpdateComplaintByAdminDTO.getDepartment());
        complaintDTO.setStatus(requestUpdateComplaintByAdminDTO.getStatus());
//        complaintDTO.setEmpId(-1L);

        if(!complaintRepository.update(complaintDTO)){
            throw new InfoException("Something is wrong complaint with id = "+requestUpdateComplaintByAdminDTO.getComplaintId()+" not updated");
        }

        return true;
    }

    @Override
    @Transactional
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
    public DTOListPage<ComplaintDTO> searchNotResolvedComplaintsForEmployee(RequestFilterComplaintDTO requestFilterComplaintDTO, Integer offset, Integer pageSize, EmployeeDTO employeeDTO) {
        System.out.println("Search Not Resolved Complaint For Employee in Complaint service with pagination "+requestFilterComplaintDTO);
        ComplaintDTO complaintDTO = modelMapper.map(requestFilterComplaintDTO,ComplaintDTO.class);
        addEmployeeDetails(employeeDTO, complaintDTO);

        return complaintRepository.searchAllComplaintsForNotResolved(complaintDTO,offset,pageSize);
    }

    @Override
    public Optional<List<ComplaintDTO>> searchResolvedComplaintsForEmployee(RequestFilterComplaintDTO requestFilterComplaintDTO, EmployeeDTO employeeDTO) {
        System.out.println("Search Resolved Complaint For Employee in Complaint service "+requestFilterComplaintDTO);
        ComplaintDTO complaintDTO = modelMapper.map(requestFilterComplaintDTO,ComplaintDTO.class);
        addEmployeeDetails(employeeDTO, complaintDTO);

        return complaintRepository.searchAllComplaintsForResolved(complaintDTO);
    }

    @Override
    public DTOListPage<ComplaintDTO> searchResolvedComplaintsForEmployee(RequestFilterComplaintDTO requestFilterComplaintDTO, Integer offset, Integer pageSize, EmployeeDTO employeeDTO) {
        System.out.println("Search Resolved Complaint For Employee in Complaint service "+requestFilterComplaintDTO);
        ComplaintDTO complaintDTO = modelMapper.map(requestFilterComplaintDTO,ComplaintDTO.class);
        addEmployeeDetails(employeeDTO, complaintDTO);

        return complaintRepository.searchAllComplaintsForResolved(complaintDTO,offset,pageSize);
    }

    @Override
    public NotificationList<ComplaintDTO> getAdminComplaintNotification() {
        System.out.println("Admin complaint notification service.");
        Optional<List<ComplaintDTO>> complaintDTOList = complaintRepository.findAdminComplaintsInNotification();

        if(complaintDTOList.isPresent()){
            NotificationList<ComplaintDTO> notificationList = new NotificationList<>();
            notificationList.setCount(complaintDTOList.get().size());
            LocalDate todaysDate = LocalDate.now();
            complaintDTOList.get().forEach(e->{
                if(todaysDate.equals(e.getCreatedDate().toLocalDate())){
                    notificationList.getToDays().add(e);
                }else{
                    notificationList.getOlder().add(e);
                }
            });

            notificationList.getOlder().sort((obj1, obj2)-> obj2.getCreatedDate().compareTo(obj1.getCreatedDate()));

            notificationList.getToDays().sort((obj1, obj2)-> obj2.getCreatedDate().compareTo(obj1.getCreatedDate()));

            return notificationList;
        }

        return new NotificationList<>();
    }

    @Override
    public NotificationList<ComplaintDTO> getDeptAdminComplaintNotification(Long deptId) {
        System.out.println("Dept Admin complaint notification service. " +deptId);
        Optional<List<ComplaintDTO>> complaintDTOList = complaintRepository.findDeptAdminComplaintsInNotification(deptId);

        if(complaintDTOList.isPresent()){
            NotificationList<ComplaintDTO> notificationList = new NotificationList<>();
            notificationList.setCount(complaintDTOList.get().size());
            LocalDate todaysDate = LocalDate.now();
            complaintDTOList.get().forEach(e->{
                if(todaysDate.equals(e.getCreatedDate().toLocalDate())){
                    notificationList.getToDays().add(e);
                }else{
                    notificationList.getOlder().add(e);
                }
            });

            notificationList.getOlder().sort((obj1, obj2)-> obj2.getCreatedDate().compareTo(obj1.getCreatedDate()));

            notificationList.getToDays().sort((obj1, obj2)-> obj2.getCreatedDate().compareTo(obj1.getCreatedDate()));

            return notificationList;
        }

        return new NotificationList<>();
    }

    @Override
    public NotificationList<ComplaintDTO> getUserComplaintNotification(Long empId, Long deptId) {
        System.out.println("user complaint notification service. " +empId+" "+deptId);
        Optional<List<ComplaintDTO>> complaintDTOList = complaintRepository.findUserComplaintsInNotification(empId, deptId);
        if(complaintDTOList.isPresent()){
            NotificationList<ComplaintDTO> notificationList = new NotificationList<>();
            notificationList.setCount(complaintDTOList.get().size());
            LocalDate todaysDate = LocalDate.now();
            complaintDTOList.get().forEach(e->{
                if(todaysDate.equals(e.getCreatedDate().toLocalDate())){
                    notificationList.getToDays().add(e);
                }else{
                    notificationList.getOlder().add(e);
                }
            });

            notificationList.getOlder().sort((obj1, obj2)-> obj2.getCreatedDate().compareTo(obj1.getCreatedDate()));

            notificationList.getToDays().sort((obj1, obj2)-> obj2.getCreatedDate().compareTo(obj1.getCreatedDate()));

            return notificationList;
        }

        return new NotificationList<>();
    }

    @Override
    @Transactional
    public List<ComplaintDTO> getAllOTPCleaningComplaints() {
        return complaintRepository.getAllOTPClearingComplaint();
    }

    @Override
    public void clearOTPs() {
        System.out.println("Clearing the OTPs of Complaint");

        List<ComplaintDTO> complaintDTOS = complaintRepository.getAllOTPClearingComplaint();

        for (ComplaintDTO complaintDTO: complaintDTOS){
            Long otpTime = TimeConversion.getDurationInMinutes(complaintDTO.getOtptime());
            if(otpTime > 3L){
                complaintDTO.setOtp(null);
                complaintDTO.setOtptime(null);
                complaintRepository.update(complaintDTO);
            }
        }
    }

    private static void addEmployeeDetails(EmployeeDTO employeeDTO, ComplaintDTO complaintDTO) {
        complaintDTO.setEmpId(employeeDTO.getId());
        complaintDTO.setDeptId(employeeDTO.getDepartmentId());
    }
}
