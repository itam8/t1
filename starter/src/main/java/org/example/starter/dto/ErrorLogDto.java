package org.example.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorLogDto {
    private String serviceName;
    private String headerKey;
    private String headerValue;
    private LogDatasourceErrorMessage message;
}
