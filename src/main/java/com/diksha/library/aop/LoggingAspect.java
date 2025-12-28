package com.diksha.library.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // All service methods
    @Around("execution(* com.diksha.library.service.*.*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();

        log.info("Entering method: {}", methodName);

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        log.info("Exiting method: {} | Time taken: {} ms",
                methodName, (endTime - startTime));

        return result;
    }
}

