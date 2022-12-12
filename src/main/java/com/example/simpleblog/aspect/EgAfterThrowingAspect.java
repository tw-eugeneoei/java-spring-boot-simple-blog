package com.example.simpleblog.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EgAfterThrowingAspect {

//    // most basic example, matches based on matching method names
//    @AfterThrowing("execution(* com.example.simpleblog.service.impl.PostServiceImpl.*(..))")
//    public void postExceptionAdvice() {
//        System.out.println("\nAfterThrowing advice example");
//    }

//    @AfterThrowing(
//            pointcut = "execution(* com.example.simpleblog.service.impl.PostServiceImpl.*(..))",
//            throwing = "ex"
//    )
//    public void postExceptionAdvice(Exception ex) {
//        System.out.println("\nAfterThrowing advice example");
//        System.out.println("Exception message: " + ex.getMessage());
//    }

    @AfterThrowing(
            pointcut = "@annotation(durian)",
            argNames = "durian, ex",
            throwing = "ex"
    )
    public void postExceptionAdvice(EgAfterReturning egAfterReturning, Exception exx) {
        System.out.println("\nAfterThrowing advice example");
        System.out.println("Exception message: " + exx.getMessage());
    }
}
