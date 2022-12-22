/* (C)2022 */
package com.example.simpleblog.aspect;

import java.util.UUID;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EgAroundAspect {

  //    @Around("execution(* com.example.simpleblog.service.impl.PostServiceImpl.getPostById(..))")
  //    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
  //        long start = System.currentTimeMillis();
  //        System.out.println("\n@Around advice example");
  //
  //        Object result = pjp.proceed();
  //
  //        long end = System.currentTimeMillis();
  //        long duration = end - start;
  //        System.out.println("Took: " + duration + " milliseconds to complete");
  //
  //        return result;
  //    }

  //    @Around("execution(* com.example.simpleblog.service.impl.PostServiceImpl.getPostById(..)) &&
  // args(postId)")
  //    public Object aroundAdvice(ProceedingJoinPoint pjp, UUID postId) throws Throwable {
  //        long start = System.currentTimeMillis();
  //        System.out.println("\n@Around advice example");
  //        System.out.println("postId: " + postId);
  //
  //        Object result = pjp.proceed();
  //
  //        long end = System.currentTimeMillis();
  //        long duration = end - start;
  //        System.out.println("Took: " + duration + " milliseconds to complete");
  //
  //        return result;
  //    }

  @Around(value = "@annotation(asd) && args(postId)", argNames = "asd, postId")
  public Object aroundAdvice(ProceedingJoinPoint pjp, EgAround egAround, UUID postId)
      throws Throwable {
    long start = System.currentTimeMillis();
    String method = pjp.getSignature().toShortString();

    System.out.println("\n@Around advice example");
    System.out.println("method: " + method);
    System.out.println("postId: " + postId);
    System.out.println("Aspect annotation value: " + egAround.value());

    Object result = pjp.proceed();

    long end = System.currentTimeMillis();
    long duration = end - start;
    System.out.println("Took: " + duration + " milliseconds to complete\n");

    return result;
  }
}
