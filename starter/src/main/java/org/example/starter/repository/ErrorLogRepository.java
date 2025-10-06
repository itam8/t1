package org.example.starter.repository;

import lombok.AllArgsConstructor;
import org.example.starter.dto.ErrorLogDto;
import org.example.starter.dto.LogDatasourceErrorMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ErrorLogRepository {
    private final JdbcTemplate jdbcTemplate;

    public void save(ErrorLogDto errorLogDto) {
        String sql = "INSERT INTO error_logs (service_name, log_type, timestamp, method_signature, " +
                "exception_stack_trace, exception_message, method_parameters) VALUES (?, ?, ?, ?, ?, ?, ?)";
        LogDatasourceErrorMessage message = errorLogDto.getMessage();
        jdbcTemplate.update(
                sql,
                errorLogDto.getServiceName(),
                errorLogDto.getHeaderValue(),
                message.getTimestamp(),
                message.getMethodSignature(),
                message.getExceptionStackTrace(),
                message.getExceptionMessage(),
                message.getMethodParameters()
        );
    }
}
