package com.xworkz.controller;


import com.xworkz.entity.ComplaintHistoryDTO;
import com.xworkz.exceptions.InfoException;
import com.xworkz.service.ComplaintHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/complaintHistory")
public class ComplaintHistoryController {

    @Autowired
    private ComplaintHistoryService complaintHistoryService;

    @GetMapping("/getComplaintHistory/{complaintId}")
    @ResponseBody
    public List<ComplaintHistoryDTO> viewComplaintHistory(@PathVariable Integer complaintId, Model model) {
        System.out.println("Fetching complaint history for complaint ID: " + complaintId);
        try {
            Optional<List<ComplaintHistoryDTO>> historyList = complaintHistoryService.getComplaintHistoryByComplaintId(complaintId);
            if(historyList.isPresent()) return historyList.get();
            return Collections.emptyList();
        } catch (Exception e) {
            System.out.println("Error occurred while fetching complaint history: " + e.getMessage());
            throw e;
        }
    }
}
