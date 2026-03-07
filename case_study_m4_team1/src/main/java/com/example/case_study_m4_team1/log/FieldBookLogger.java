package com.example.case_study_m4_team1.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FieldBookLogger {
    @Around("execution(* com.example.case_study_m4_team1.service.booking.IFieldBookService.*(..))")
    public Object logFieldBookService(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("START:" + methodName);
        Object result = joinPoint.proceed();
        System.out.println("END:" + methodName);
        return result;
    }
}
