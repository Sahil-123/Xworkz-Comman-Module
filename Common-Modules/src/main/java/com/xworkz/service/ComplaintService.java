package com.xworkz.service;

import com.xworkz.dto.DTOListPage;
import com.xworkz.dto.NotificationList;
import com.xworkz.entity.ComplaintDTO;
import com.xworkz.entity.EmployeeDTO;
import com.xworkz.entity.UserDTO;
import com.xworkz.requestDto.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

public interface ComplaintService {
    List<ComplaintDTO> findAllComplaints();
    Optional<ComplaintDTO> findComplaintById(Long id);
    ComplaintDTO saveComplaint(RequestComplaintDTO requestComplaintDTO, UserDTO userDTO);
    void deleteComplaint(Long id);
    ComplaintDTO updateComplaint(ComplaintDTO complaint);
//    Optional<List<ComplaintDTO>> searchComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO);

    ComplaintDTO searchComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO);

    DTOListPage<ComplaintDTO> searchComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO, Integer offset, Integer pageSize);

    DTOListPage<ComplaintDTO> searchComplaintsForAdmin(RequestFilterComplaintDTO requestFilterComplaintDTO, Integer offset, Integer pageSize);
    Boolean updateComplaint(RequestUpdateComplaintDTO requestUpdateComplaintDTO);

    Boolean updateComplaintForAdmin(RequestUpdateComplaintByAdminDTO requestUpdateComplaintByAdminDTO, Model model);

    Boolean updateComplaintForDepartmentAdmin(RequestUpdateDepartmentComplaintByAdminDTO requestUpdateDepartmentComplaintByAdminDTO, Model model);


    Optional<List<ComplaintDTO>> searchNotResolvedComplaintsForEmployee(RequestFilterComplaintDTO requestFilterComplaintDTO, EmployeeDTO employeeDTO);

    DTOListPage<ComplaintDTO> searchNotResolvedComplaintsForEmployee(RequestFilterComplaintDTO requestFilterComplaintDTO, Integer offset,Integer pageSize ,EmployeeDTO employeeDTO);

    Optional<List<ComplaintDTO>> searchResolvedComplaintsForEmployee(RequestFilterComplaintDTO requestFilterComplaintDTO, EmployeeDTO employeeDTO);

    DTOListPage<ComplaintDTO> searchResolvedComplaintsForEmployee(RequestFilterComplaintDTO requestFilterComplaintDTO,Integer offset,Integer pageSize, EmployeeDTO employeeDTO);

    NotificationList<ComplaintDTO> getAdminComplaintNotification();

    NotificationList<ComplaintDTO> getDeptAdminComplaintNotification(Long deptId);

    NotificationList<ComplaintDTO> getUserComplaintNotification(Long empId, Long deptId);

    List<ComplaintDTO> getAllOTPCleaningComplaints();

    void clearOTPs();
}
