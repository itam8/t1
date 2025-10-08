package org.example.starter.config;

import org.example.starter.aspect.CachedAspect;
import org.example.starter.aspect.HttpOutcomeRequestLogAspect;
import org.example.starter.aspect.LogDatasourceErrorAspect;
import org.example.starter.aspect.MetricAspect;
import org.example.starter.kafka.KafkaStarterProducer;
import org.example.starter.repository.ErrorLogRepository;
import org.example.starter.service.ErrorLogService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@AutoConfiguration
@PropertySource("classpath:/application.properties")
@EnableScheduling
public class LoggingAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ErrorLogRepository errorLogRepository(JdbcTemplate jdbcTemplate) {
        return new ErrorLogRepository(jdbcTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorLogService errorLogService(ErrorLogRepository errorLogRepository) {
        return new ErrorLogService(errorLogRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaStarterProducer kafkaStarterProducer(
            KafkaTemplate<String, Object> kafkaTemplate,
            ErrorLogService errorLogService
    ) {
        return new KafkaStarterProducer(kafkaTemplate, errorLogService);
    }

    @Bean
    @ConditionalOnMissingBean
    public LogDatasourceErrorAspect logDatasourceErrorAspect(KafkaStarterProducer kafkaStarterProducer) {
        return new LogDatasourceErrorAspect(kafkaStarterProducer);
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpOutcomeRequestLogAspect httpOutcomeRequestLogAspect(KafkaStarterProducer kafkaStarterProducer) {
        return new HttpOutcomeRequestLogAspect(kafkaStarterProducer);
    }

    @Bean
    @ConditionalOnMissingBean
    public MetricAspect metricAspect(KafkaStarterProducer kafkaStarterProducer) {
        return new MetricAspect(kafkaStarterProducer);
    }

    @Bean
    @ConditionalOnMissingBean
    public CachedAspect cachedAspect() {
        return new CachedAspect();
    }
}
