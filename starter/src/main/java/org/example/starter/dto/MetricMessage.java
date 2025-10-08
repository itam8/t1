package org.example.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricMessage {
    private String methodSignature;
    private long executionTime;
    private String methodParameters;
}
