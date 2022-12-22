/* (C)2022 */
package com.example.simpleblog.aspect;

import java.util.UUID;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

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
