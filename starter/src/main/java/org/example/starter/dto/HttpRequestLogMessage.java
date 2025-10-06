package org.example.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequestLogMessage {
    private LocalDateTime timestamp;
    private String methodSignature;
    private String methodParameters;
}
