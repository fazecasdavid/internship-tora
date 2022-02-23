package com.tora.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(com.tora.service.*)")
    private void inService() {
    }

    @Around("inService()")
    public Object logService(ProceedingJoinPoint jp) throws Throwable {
        logger.info("Order service: " + jp.getArgs()[0]);
        var proceed = jp.proceed();
        logger.info("After service call: " + jp.getArgs()[0]);
        return proceed;
    }
}
