package com.xworkz.utils;

import com.xworkz.dto.DTOListPage;
import org.springframework.ui.Model;

import java.util.ArrayList;

public class CommonUtils {

    public static final String IN_PROGRESS = "In Progress";
    public static final String PENDING= "Pending";
    public static final String NOT_RESOLVED="Not Resolved";
    public static final String RESOLVED="Resolved";
    public static final Integer PAGE_SIZE= 10;

    public static final Integer DEFAULT_PAGE_SIZE= 10;

    public static void setPagination(Integer offset, Integer pageSize, String pageURL, DTOListPage<?> userDTODTOListPage, int pagesCount, Model model) {
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageURL", pageURL);
        model.addAttribute("currentPage", offset);
        model.addAttribute("totalRecordCount", userDTODTOListPage.getCount());
        model.addAttribute("pages", pagesCount);
        model.addAttribute("currentPageRecordSize",userDTODTOListPage.getList().orElse(new ArrayList<>()).size());
    }

}
