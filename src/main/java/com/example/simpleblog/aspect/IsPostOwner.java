/* (C)2022 */
package com.example.simpleblog.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Retention just states whether the annotation will be available to the JVM at runtime or not. By
// default it is not, so Spring AOP would not be able to see the annotation
@Retention(RetentionPolicy.RUNTIME)
// @Target annotation tells us where our annotation will be applicable. Here we are using
// ElementType.Method, which means it will only work on methods
@Target(ElementType.METHOD)
public @interface IsPostOwner {
  String method() default "unknown";
}
