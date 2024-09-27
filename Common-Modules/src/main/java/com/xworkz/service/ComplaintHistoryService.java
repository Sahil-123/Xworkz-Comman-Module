package com.xworkz.service;

import com.xworkz.entity.ComplaintHistoryDTO;

import java.util.List;
import java.util.Optional;

public interface ComplaintHistoryService {

    boolean saveComplaintHistory(ComplaintHistoryDTO complaintHistoryDTO);

    Optional<List<ComplaintHistoryDTO>> getComplaintHistoryByComplaintId(Integer complaintID);

}
