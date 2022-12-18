package com.example.simpleblog.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class EgAfterAspect {

//    // most basic example, matches based on matching method names
//    @After("execution(* com.example.simpleblog.service.impl.PostServiceImpl.*(..))")
//    public void afterAdvice() {
//        System.out.println("\n@After advice example");
//    }

    @After(value = "@annotation(asd) && args(id, ..)", argNames = "asd, id")
    public void afterAdvice(EgAfter egAfter, UUID postId) {
        System.out.println("\n@After advice example");
        System.out.println("postId: " + postId);
        System.out.println("Aspect annotation value: " + egAfter.value() + "\n");
    }
}
