/* (C)2022 */
package com.example.simpleblog.aspect;

import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class LoggerAspect {

    private final Logger logger = Logger.getLogger(getClass().getName());

    // *.*.(..) ---> matches any class in package, any method in the class and any number of
    // arguments
    @Pointcut("execution(* com.example.simpleblog.controller.*.*(..))")
    private void forControllerPackage() {}

    @Pointcut("execution(* com.example.simpleblog.service.*.*(..))")
    private void forServicePackage() {}

    @Pointcut("forControllerPackage() || forServicePackage()")
    private void forControllerOrServicePackage() {}

    @Before("forControllerOrServicePackage()")
    private void loggingBeforeAspect(JoinPoint joinPoint) {
        logger.info("logging @Before forControllerPackage and forServicePackage");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        logger.info("method: " + methodSignature.toShortString());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logger.info("arg: " + arg);
            // arg here can be cast and subsequently accessed if necessary
        }
        System.out.println("\n");
    }

    @AfterReturning(pointcut = "forControllerOrServicePackage()", returning = "result")
    private void loggingAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("logging @AfterReturning forControllerPackage and forServicePackage");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        logger.info("method: " + methodSignature.toShortString());
        logger.info("result: " + result);
        System.out.println("\n");
    }

    //    @Before("@annotation(logger)")
    //    public void loggerAdvice(JoinPoint joinPoint, Logger logger) {
    ////        System.out.println(joinPoint.getSignature());
    //        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    ////        System.out.println((methodSignature.getMethod()));
    ////        System.out.println((methodSignature.getName()));
    //
    //        System.out.println("\nMethod: " + methodSignature);
    //
    //        Object[] args = joinPoint.getArgs();
    //        for (Object arg : args) {
    //            System.out.println("arg: " + arg);
    //            // arg here can be cast and subsequently accessed if necessary
    //        }
    //
    //        System.out.printf("\n>>> Logger aspect value: %s\n%n", logger.value());
    //    }
}
