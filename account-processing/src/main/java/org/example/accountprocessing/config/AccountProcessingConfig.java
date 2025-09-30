package org.example.accountprocessing.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Configuration
public class AccountProcessingConfig {
    @Value("${transactions.count.max:10}")
    private Integer maxTransactionsCount;
    @Value("${period.max:1h}")
    private Duration maxPeriod;
}
