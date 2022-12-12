package com.example.simpleblog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class LoggerAspect {

    @Before("@annotation(logger)")
    public void loggerAdvice(JoinPoint joinPoint, Logger logger) {
//        System.out.println(joinPoint.getSignature());
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        System.out.println((methodSignature.getMethod()));
//        System.out.println((methodSignature.getName()));

        System.out.println("\nMethod: " + methodSignature);

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            System.out.println("arg: " + arg);
            // arg here can be cast and subsequently accessed if necessary
        }

        System.out.printf("\n>>> Logger aspect value: %s\n%n", logger.value());
    }
}
