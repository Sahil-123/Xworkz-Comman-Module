package com.xworkz.repository;

import com.xworkz.dto.DTOListPage;
import com.xworkz.entity.ComplaintDTO;
import com.xworkz.requestDto.RequestUpdateComplaintDTO;

import java.util.List;
import java.util.Optional;

public interface ComplaintRepository {
    ComplaintDTO save(ComplaintDTO complaintDTO);

    Optional<List<ComplaintDTO>> findAll();

    Optional<ComplaintDTO> findById(Long id);

    Boolean update(ComplaintDTO complaintDTO);

    Boolean deleteById(Long id);

    DTOListPage<ComplaintDTO> searchComplaints(ComplaintDTO complaintDTO, Integer offset, Integer pageSize);

    DTOListPage<ComplaintDTO> searchAllComplaintsForAdmin(ComplaintDTO complaintDTO, Integer offset, Integer pageSize);

    Optional<List<ComplaintDTO>> searchAllComplaintsForAdmin(ComplaintDTO complaintDTO);

//    Optional<List<ComplaintDTO>> findUserComplaintsInNotification(Long empId, Long deptId);

    Boolean updateComplaint(RequestUpdateComplaintDTO requestUpdateComplaintDTO);

    public Optional<List<ComplaintDTO>> searchAllComplaintsForNotResolved(ComplaintDTO complaintDTO);

    public DTOListPage<ComplaintDTO > searchAllComplaintsForNotResolved(ComplaintDTO complaintDTO, Integer offset, Integer pageSize);


    public Optional<List<ComplaintDTO>> searchAllComplaintsForResolved(ComplaintDTO complaintDTO);

    public DTOListPage<ComplaintDTO > searchAllComplaintsForResolved(ComplaintDTO complaintDTO, Integer offset, Integer pageSize);

    Optional<List<ComplaintDTO>> findAdminComplaintsInNotification();

    Optional<List<ComplaintDTO>> findDeptAdminComplaintsInNotification(Long deptId);

    Optional<List<ComplaintDTO>> findUserComplaintsInNotification(Long empId, Long deptId);

    List<ComplaintDTO> getAllOTPClearingComplaint();

}
