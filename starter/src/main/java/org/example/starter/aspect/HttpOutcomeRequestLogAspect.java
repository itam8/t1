package org.example.starter.aspect;

import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.example.starter.dto.HttpRequestLogDto;
import org.example.starter.dto.HttpRequestLogMessage;
import org.example.starter.kafka.KafkaStarterProducer;
import org.example.starter.model.LogType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@AllArgsConstructor
public class HttpOutcomeRequestLogAspect {
    private final KafkaStarterProducer kafkaStarterProducer;

    @AfterReturning(pointcut = "@annotation(org.example.starter.annotation.HttpOutcomeRequestLog)", returning = "result")
    public void httpOutcomeRequestLog(JoinPoint joinPoint, Object result) {
        HttpRequestLogMessage httpRequestLogMessage = new HttpRequestLogMessage(
                LocalDateTime.now(),
                joinPoint.getSignature().toString(),
                Arrays.toString(joinPoint.getArgs())
        );
        kafkaStarterProducer.sendServiceLogMessage(new HttpRequestLogDto(
                joinPoint.getTarget().getClass().getSimpleName(),
                "type",
                LogType.INFO.name(),
                httpRequestLogMessage
        ));
    }
}