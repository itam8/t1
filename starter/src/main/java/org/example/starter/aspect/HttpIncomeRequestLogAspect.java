package org.example.starter.aspect;

import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
public class HttpIncomeRequestLogAspect {
    private final KafkaStarterProducer kafkaStarterProducer;

    @Before("@annotation(org.example.starter.annotation.HttpIncomeRequestLog)")
    public void httpIncomeRequestLog(JoinPoint joinPoint) {
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
