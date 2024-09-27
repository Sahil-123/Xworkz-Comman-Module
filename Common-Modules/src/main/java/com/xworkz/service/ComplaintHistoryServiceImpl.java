package com.xworkz.service;

import com.xworkz.entity.ComplaintHistoryDTO;
import com.xworkz.repository.ComplaintHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintHistoryServiceImpl implements ComplaintHistoryService {

    @Autowired
    private ComplaintHistoryRepository complaintHistoryRepository;


    @Override
    @Transactional
    public boolean saveComplaintHistory(ComplaintHistoryDTO complaintHistoryDTO) {
        System.out.println("Complaint history save process is initiated.");
        complaintHistoryDTO.setCreatedDate(LocalDateTime.now());
        return complaintHistoryRepository.save(complaintHistoryDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ComplaintHistoryDTO>> getComplaintHistoryByComplaintId(Integer complaintID) {
        System.out.println("Fetching complaint history for complaintID: " + complaintID);
        return complaintHistoryRepository.getHistoryByComplaintId(complaintID);
    }

}
