package org.example.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricDto {
    private String serviceName;
    private String headerKey;
    private String headerValue;
    private MetricMessage message;
}
