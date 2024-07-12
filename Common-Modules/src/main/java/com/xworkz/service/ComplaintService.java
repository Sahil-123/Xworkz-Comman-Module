package com.xworkz.service;

import com.xworkz.dto.ComplaintDTO;
import com.xworkz.dto.UserDTO;
import com.xworkz.requestDto.*;

import java.util.List;
import java.util.Optional;

public interface ComplaintService {
    List<ComplaintDTO> findAllComplaints();
    Optional<ComplaintDTO> findComplaintById(Long id);
    ComplaintDTO saveComplaint(RequestComplaintDTO requestComplaintDTO, UserDTO userDTO);
    void deleteComplaint(Long id);
    ComplaintDTO updateComplaint(ComplaintDTO complaint);
    Optional<List<ComplaintDTO>> searchComplaints(RequestFilterComplaintDTO requestFilterComplaintDTO);

    Optional<List<ComplaintDTO>> searchComplaintsForAdmin(RequestFilterComplaintDTO requestFilterComplaintDTO);
    Boolean updateComplaint(RequestUpdateComplaintDTO requestUpdateComplaintDTO);

    Boolean updateComplaintForAdmin(RequestUpdateComplaintByAdminDTO requestUpdateComplaintByAdminDTO);

    Boolean updateComplaintForDepartmentAdmin(RequestUpdateDepartmentComplaintByAdminDTO requestUpdateDepartmentComplaintByAdminDTO);



}
