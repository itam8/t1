package org.example.creditprocessing.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class CreditProcessingConfig {

    @Value("${credit.limit.max:1000000}")
    private Double maxCreditLimit;

}
