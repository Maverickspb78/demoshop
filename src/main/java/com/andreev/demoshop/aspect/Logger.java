package com.andreev.demoshop.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.*;
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
    public void logBefore(JoinPoint joinPoint) {
        LocalDateTime localNow = LocalDateTime.now();
        ZonedDateTime zonedUTC = localNow.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedIST = zonedUTC.withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        loggerList.add("[" + LocalDate.now() + " " +
                zonedIST.truncatedTo(ChronoUnit.SECONDS) + "] Before: " + joinPoint.getSignature());
    }

    @AfterReturning("logger()")
    public void logAfterReturning(JoinPoint joinPoint) {
        LocalDateTime localNow = LocalDateTime.now();
        ZonedDateTime zonedUTC = localNow.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedIST = zonedUTC.withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        loggerList.add("[" + LocalDate.now() + " " +
                zonedIST.truncatedTo(ChronoUnit.SECONDS) + "] AfterReturning: " + joinPoint.getSignature());
    }

    @AfterThrowing("logger()")
    public void logAfterThrowing(JoinPoint joinPoint) {
        LocalDateTime localNow = LocalDateTime.now();
        ZonedDateTime zonedUTC = localNow.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedIST = zonedUTC.withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        loggerList.add("[" + LocalDate.now() + " " +
                zonedIST.truncatedTo(ChronoUnit.SECONDS) + "] AfterThrowing: " + joinPoint.getSignature());
    }

    @Around("logger()")
    public Object logAround(ProceedingJoinPoint pjp) {
        LocalDateTime localNow = LocalDateTime.now();
        ZonedDateTime zonedUTC = localNow.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedIST = zonedUTC.withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        Object value;
        loggerList.add("[" + LocalDate.now() + " " +
                zonedIST.truncatedTo(ChronoUnit.SECONDS) + "] Around before: " + pjp.getSignature());
        try {
            value = pjp.proceed();
            loggerList.add("[" + LocalDate.now() + " " +
                    zonedIST.truncatedTo(ChronoUnit.SECONDS) + "] Around: " + pjp.getSignature());
        } catch (Throwable t) {
            loggerList.add("[" + LocalDate.now() + " " +
                    zonedIST.truncatedTo(ChronoUnit.SECONDS) + "] Around with Error: " + pjp.getSignature());
            return null;
        }
        loggerList.add("[" + LocalDate.now() + " " +
                zonedIST.truncatedTo(ChronoUnit.SECONDS) + "] Around after: " + pjp.getSignature());
        return value;
    }
}
