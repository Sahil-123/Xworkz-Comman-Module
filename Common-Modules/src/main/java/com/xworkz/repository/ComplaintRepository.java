package com.xworkz.repository;

import com.xworkz.dto.ComplaintDTO;
import com.xworkz.requestDto.RequestUpdateComplaintDTO;

import java.util.List;
import java.util.Optional;

public interface ComplaintRepository {
    Boolean save(ComplaintDTO complaintDTO);

    Optional<List<ComplaintDTO>> findAll();

    Optional<ComplaintDTO> findById(Long id);

    Boolean update(ComplaintDTO complaintDTO);

    Boolean deleteById(Long id);

    Optional<List<ComplaintDTO>> searchComplaints(ComplaintDTO complaintDTO);

    Optional<List<ComplaintDTO>> searchAllComplaintsForAdmin(ComplaintDTO complaintDTO);

    Boolean updateComplaint(RequestUpdateComplaintDTO requestUpdateComplaintDTO);

    public Optional<List<ComplaintDTO>> searchAllComplaintsForNotResolved(ComplaintDTO complaintDTO);

    public Optional<List<ComplaintDTO>> searchAllComplaintsForResolved(ComplaintDTO complaintDTO);

}
