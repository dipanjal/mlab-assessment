package com.mlab.assessment.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static java.lang.String.format;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    private final ObjectMapper jacksonMapper;

    @Pointcut("execution(public * com.mlab.assessment.service..*(..))")
    public void serviceLoggingPointcut() {
        /** Matches all the public methods of Services */
    }

    @Pointcut("@within(com.mlab.assessment.annotation.EnableLogging)")
    public void enableLoggingPointcut() {
        /** Matches Components with @EnableLogging annotation */
    }

    @Around(value = "serviceLoggingPointcut() && enableLoggingPointcut()")
    public Object performanceLoggingAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        traceLog(joinPoint);
        return this.proceedAndLogPerformance(joinPoint);
    }

    private void traceLog(JoinPoint joinPoint){
        StringBuilder builder = new StringBuilder("\n-----------------------\n")
                .append(format("Class: %s%n", joinPoint.getSignature().getDeclaringTypeName()))
                .append(format("Method: %s%n", joinPoint.getSignature().getName()));

        AtomicInteger count = new AtomicInteger();
        Arrays.stream(joinPoint.getArgs())
                .forEach(argsAppender(builder, count));

        builder.append("-------------------------");
        log.info(builder.toString());
    }

    private Consumer<Object> argsAppender(StringBuilder builder, AtomicInteger count){
        return o -> {
            try {
                String json = jacksonMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(o);
                builder.append(
                        format("Args [%s]: %s%n",
                                count.incrementAndGet(),
                                json));
            } catch (JsonProcessingException e) {
                log.info("Error occurred while parsing Args");
                log.error(e.getMessage());
            }
        };
    }

    private Object proceedAndLogPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long entryTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - entryTime;

        String performance = String
                .format("Executed %s - %s taken %d milliseconds",
                        joinPoint.getTarget().getClass().getName(),
                        joinPoint.getSignature().getName(),
                        executionTime);
        log.info(performance);
        return result;
    }

}
