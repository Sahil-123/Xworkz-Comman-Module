package com.xworkz.repository;

import com.xworkz.entity.ComplaintHistoryDTO;

import java.util.List;
import java.util.Optional;

public interface ComplaintHistoryRepository {

    boolean save(ComplaintHistoryDTO complaintHistoryDTO);

    Optional<List<ComplaintHistoryDTO>> getHistoryByComplaintId(Integer complaintID);
}
