package com.xworkz.utils;

import com.xworkz.dto.DTOListPage;
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

}
