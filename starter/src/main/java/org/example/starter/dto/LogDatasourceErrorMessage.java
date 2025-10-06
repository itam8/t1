package org.example.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.Signature;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDatasourceErrorMessage {
    private LocalDateTime timestamp;
    private String methodSignature;
    private String exceptionStackTrace;
    private String exceptionMessage;
    private String methodParameters;
}
