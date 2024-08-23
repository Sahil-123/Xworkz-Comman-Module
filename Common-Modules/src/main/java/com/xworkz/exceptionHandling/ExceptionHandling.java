package com.xworkz.exceptionHandling;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@Controller
@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(value = Exception.class)
    public String handleGlobalException(Model model,Exception e){
        log.error("Error Occurred : "+e.getMessage());
//        System.out.println(e.getMessage()+" "+e);

        model.addAttribute("errorMessage","Error Occurred : "+e.getMessage());
        return "errorPages/error";
    }
}
