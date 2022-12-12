package com.example.simpleblog.aspect;

import com.example.simpleblog.dto.PostDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
@Component
public class EgAfterReturningAspect {

//    // most basic example, matches based on matching method names
//    @AfterReturning("execution(* com.example.simpleblog.service.impl.PostServiceImpl.get*(..))")
//    public void getAdvice() {
//        System.out.println("\nAfterReturning advice example");
//    }

//    @AfterReturning(
//            pointcut = "execution(* com.example.simpleblog.service.impl.PostServiceImpl.get*(..)) && args(postId)",
//            returning = "result"
//    )
//    public void getAdvice(Object result, UUID postId) {
//        System.out.println("\nAfterReturning advice example");
//        System.out.println("postId: " + postId);
//        System.out.println("result: " + result);
//
//        // cant seem to access annotation method this way when jp is set as first param?
//        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
//        Method method = methodSignature.getMethod();
//        EgAfterReturning egAfterReturning = method.getAnnotation(EgAfterReturning.class);
//        System.out.println("@EgAfterReturning: " + egAfterReturning); // this gives null
//        System.out.println("Aspect annotation value: " + asd.value());
//    }

    // matches based on annotation declaration
    @AfterReturning(
            pointcut = "@annotation(apple) && args(postId)",
            argNames = "apple, result, postId",
            returning = "result"
    )
    public void getAdvice(JoinPoint jp, EgAfterReturning egAfterReturning, PostDto result, UUID postId) {
        System.out.println("\nAfterReturning advice example");
        System.out.println("method: " + jp.getSignature().toShortString());
        System.out.println("postId: " + postId);
        System.out.println("result: " + result);
        System.out.println("content: " + result.getContent());
        // result.setContent("HELLO"); // can modify result
        System.out.println("Aspect annotation value: " + egAfterReturning.value() + "\n");
    }
}
