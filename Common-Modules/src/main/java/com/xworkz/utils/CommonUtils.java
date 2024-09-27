package com.xworkz.utils;

import com.xworkz.dto.DTOListPage;
import com.xworkz.enums.ComplaintStatus;
import org.springframework.ui.Model;

import java.util.ArrayList;

public class CommonUtils {

    public static final String IN_PROGRESS = "InProgress";
    public static final String PENDING= "Pending";
    public static final String NOT_RESOLVED="Not Resolved";
    public static final String RESOLVED="Resolved";


    public static final Integer PAGE_SIZE= 10;
    public static final Integer DEFAULT_PAGE_SIZE= 10;

    public static final Long DEPARTMENT_ADMIN_NUMBER_OF_ATTEMPTS_ALLOWED_IN_HOURS= 1L;
    public static final Long DEPARTMENT_ADMIN_PASSWORD_RESET_DURATION= 10L;


    public static void setPagination(Integer offset, Integer pageSize, String pageURL, DTOListPage<?> listPage, Model model) {
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageURL", pageURL);
        model.addAttribute("currentPage", offset);
        model.addAttribute("totalRecordCount", listPage.getCount());
        model.addAttribute("pages", findPageCount(listPage.getCount(),pageSize));
        model.addAttribute("currentPageRecordSize",listPage.getList().orElse(new ArrayList<>()).size());
    }

    private static Integer findPageCount(Long totalRecords,Integer pageSize){
        Integer result = Math.toIntExact(totalRecords / pageSize.longValue());

        if(totalRecords % pageSize != 0){
            result++;
        }
        return result;
    }

    public static int getFirstResultForPagination(Integer offset, Integer pageSize) {
        return (offset - 1) * pageSize;
    }

    public static String getComplaintStatusMessage(ComplaintStatus status) {
        switch (status) {
            case RECEIVED:
                return "Your complaint has been submitted and is currently under review by the administration.";
            case OPEN:
                return "Your complaint is now open and awaiting further action.";
            case IN_REVIEW:
                return "Your complaint is currently under review by the administration.";
            case ASSIGNED_TO_DEPARTMENT:
                return "Your complaint has been assigned to the relevant department for further investigation.";
            case ASSIGNED_TO_EMPLOYEE:
                return "Your complaint has been assigned to a specific employee for resolution.";
            case IN_PROGRESS:
                return "The resolution process for your complaint is currently in progress.";
            case PENDING:
                return "Your complaint is pending further information or action from you.";
            case RESOLVED:
                return "Your complaint has been successfully resolved.";
            case REOPENED:
                return "Your complaint has been reopened for further review.";
            case NOT_RESOLVED:
                return "Your complaint could not be resolved at this time and requires further attention.";
            case REJECTED:
                return "Your complaint has been reviewed and unfortunately rejected.";
            default:
                return "Unknown complaint status.";
        }
    }
}
