package org.example.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequestLogDto {
    private String serviceName;
    private String headerKey;
    private String headerValue;
    private HttpRequestLogMessage message;
}
