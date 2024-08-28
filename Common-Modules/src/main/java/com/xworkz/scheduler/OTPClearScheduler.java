package com.xworkz.scheduler;

import com.xworkz.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OTPClearScheduler {

    @Autowired
    private ComplaintService complaintService;

    public OTPClearScheduler() {
        System.out.println("OTP Clearing Scheduler is initiated");
    }

    @Scheduled(fixedRate = (1000 * 60 * 60 * 24))
//    @Scheduled(cron = "0 0 0 * * *")
    public void clearOTPs() {
        System.out.println("Clearing OTPs");
//        System.out.println(complaintService.getAllOTPCleaningComplaints());
        complaintService.clearOTPs();
    }
}
