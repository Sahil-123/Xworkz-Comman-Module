package com.xworkz.service;

import com.xworkz.dto.ComplaintDTO;
import com.xworkz.dto.UserDTO;
import com.xworkz.requestDto.RequestComplaintDTO;
import com.xworkz.requestDto.RequestFilterComplaintDTO;
import com.xworkz.requestDto.RequestUpdateComplaintByAdminDTO;
import com.xworkz.requestDto.RequestUpdateComplaintDTO;

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

}
