package org.example.starter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "error_logs")
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "service_name")
    private String serviceName;
    @Column(name = "log_type")
    private String logType;
    private LocalDateTime timestamp;
    @Column(name = "method_signature")
    private String methodSignature;
    @Column(name = "exception_stack_trace")
    private String exceptionStackTrace;
    @Column(name = "exception_message")
    private String exceptionMessage;
    @Column(name = "method_parameters")
    private String methodParameters;
}
