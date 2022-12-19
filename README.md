# Java Spring Boot Simple Blog

- https://projectlombok.org/features/Builder
- https://www.baeldung.com/hibernate-notnull-vs-nullable
- https://www.baeldung.com/lombok-builder
- https://www.baeldung.com/spring-security-method-security

# Ressources

- https://spring.io/guides/gs/spring-boot/
- https://reflectoring.io/spring-boot-test/
- https://www.toptal.com/spring/spring-security-tutorial

# Seed

`mysql -u yourusername -p yourpassword spring_boot_simple_blog < ./src/main/resources/`

# AOP

- **Aspect**: module of code for a cross-cutting concern eg. logging, security
- **Advice**: what action is taken and when it should be applied
  - Before advice: Advice that executes before a join point, but which does not have the ability to prevent execution flow proceeding to the join point (unless it throws an exception).
  - After returning advice: Advice to be executed after a join point completes normally: for example, if a method returns without throwing an exception.
  - After throwing advice: Advice to be executed if a method exits by throwing an exception. Note that exception **will still be propagated** to the calling programme. To stop exception propagation, use **@Around** instead. Use cases include:
    - Log exception
    - Perform auditing on exception
    - Notify DevOps team via email or sms e.g. during 500 exception
  - After (finally) advice: Advice to be executed regardless of the means by which a join point exits (normal or exceptional return) ie runs after a method is completed regardless of the outcome/exceptions
    - Log exception
    - Perform auditing on exception
    - code to run regardless of method outcome eg cleaning up of resources
  - Around advice: Advice that surrounds a join point such as a method invocation. This is the most powerful kind of advice. Around advice can perform custom behavior before and after the method invocation. It is also responsible for choosing whether to proceed to the join point or to shortcut the advised method execution by returning its own return value or throwing an exception.
    - runs before and after method call
    - logging, auditing, security
    - pre-processing and post-processing data
    - instrumentation and profiling code eg how long does it take for a section of code to run
    - managing exceptions ie swallow/handle/stop exceptions
    - get reference to ProceedingJointPoint which gives access to the target method where advice can decide whether to execute target method

- **Join Point**: when to apply the code during programme execution
- **Pointcut**: a predicate expression for where advice should be applied


