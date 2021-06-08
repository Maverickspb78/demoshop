package com.andreev.demoshop.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Aspect
@Component
public class Logger {

    public static List<String> loggerList = Collections.synchronizedList(new ArrayList<>());

    @Pointcut("execution(* com.andreev.demoshop.controllers..*.*(..))")
    private void logger(){}

    @Before("logger()")
    public void logBefore(JoinPoint joinPoint){
        loggerList.add("[" + LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) +
                "] Before: " + joinPoint.getSignature());
    }

    @AfterReturning("logger()")
    public void logAfterReturning(JoinPoint joinPoint) {
        loggerList.add("[" + LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) +
                "] AfterReturning: " + joinPoint.getSignature());
    }

    @AfterThrowing("logger()")
    public void logAfterThrowing(JoinPoint joinPoint) {
        loggerList.add("[" + LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) +
                "] AfterThrowing: " + joinPoint.getSignature());
        System.out.println("Log AfterThrowing : " + joinPoint);
    }

    @Around("logger()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        loggerList.add("[" + LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) +
                "] Around before: " + pjp.getSignature());
        Object value;
        try {
            value= pjp.proceed();
            loggerList.add("[" + LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) +
                    "] Around: " + pjp.getSignature());
        } catch (Throwable t) {
            loggerList.add("[" + LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) +
                    "] Around Error: " + pjp.getSignature());
            return null;
        }
        loggerList.add("[" + LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) +
                "] Around after: " + pjp.getSignature());
        return value;
    }
}
