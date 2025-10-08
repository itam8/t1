package org.example.starter.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.starter.dto.MetricDto;
import org.example.starter.dto.MetricMessage;
import org.example.starter.kafka.KafkaStarterProducer;
import org.example.starter.model.LogType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class MetricAspect {
    private final KafkaStarterProducer kafkaStarterProducer;
    @Value("${time.limit}")
    private long timeLimit;

    @Around("@annotation(org.example.starter.annotation.Metric)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result;

        try {
            result = joinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;

            if (executionTime > timeLimit) {
                MetricMessage metricMessage = new MetricMessage(
                        joinPoint.getSignature().toString(),
                        executionTime,
                        Arrays.toString(joinPoint.getArgs())
                );
                String[] names = joinPoint.getTarget().getClass().getName().split("\\.");
                kafkaStarterProducer.sendServiceLogMessage(new MetricDto(
                        names[2],
                        "type",
                        LogType.WARNING.name(),
                        metricMessage
                ));
            }
        }

        return result;
    }
}
