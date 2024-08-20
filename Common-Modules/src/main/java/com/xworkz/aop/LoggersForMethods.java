package com.xworkz.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggersForMethods {

//    @Before("execution(* com.xworkz.controller.*.*(..)) || execution(* com.xworkz.service.*.*(..)) || execution(* com.xworkz.repository.*.*(..)) ")
//    public void loggingTheMethods(JoinPoint joinPoint){
//        log.info("Process executed in "+joinPoint.getSignature().getName());
//    }

    @Before("execution(* com.xworkz.controller.*.*(..)) || execution(* com.xworkz.service.*.*(..)) || execution(* com.xworkz.repository.*.*(..)) ")
    public void beforeMethod(JoinPoint joinPoint) {
        log.info("Starting method: " + joinPoint.getSignature()+"\n Arguments : "+Arrays.toString(joinPoint.getArgs()));
    }

    @After("execution(* com.xworkz.controller.*.*(..)) || execution(* com.xworkz.service.*.*(..)) || execution(* com.xworkz.repository.*.*(..)) ")
    public void afterMethod(JoinPoint joinPoint) {
        log.info("Completed method: "+ joinPoint.getSignature());
    }

}
