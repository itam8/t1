package org.example.starter.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.example.starter.dto.ErrorLogDto;
import org.example.starter.dto.LogDatasourceErrorMessage;
import org.example.starter.kafka.KafkaStarterProducer;
import org.example.starter.model.LogType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class LogDatasourceErrorAspect {
    private final KafkaStarterProducer kafkaStarterProducer;

    @AfterThrowing(pointcut = "@annotation(org.example.starter.annotation.LogDatasourceError)", throwing = "exception")
    public void logDatasourceError(JoinPoint joinPoint, Exception exception) {
        LogDatasourceErrorMessage logDatasourceErrorMessage = new LogDatasourceErrorMessage(
                LocalDateTime.now(),
                joinPoint.getSignature().toString(),
                Arrays.toString(exception.getStackTrace()),
                exception.getMessage(),
                Arrays.toString(joinPoint.getArgs())
        );
        String[] names = joinPoint.getTarget().getClass().getName().split("\\.");
        kafkaStarterProducer.sendServiceLogMessage(new ErrorLogDto(
                names[2],
                "type",
                LogType.ERROR.name(),
                logDatasourceErrorMessage
        ));
    }
}
