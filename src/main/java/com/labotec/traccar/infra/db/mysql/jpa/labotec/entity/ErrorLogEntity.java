package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "error_logs")
@NoArgsConstructor
public class ErrorLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "status_code", nullable = false)
    private int statusCode;
    @Column(name = "path_variables",nullable = true)
    private String pathVariables;
    @Column(name = "error_message", nullable = false, length = 1000)
    private String errorMessage;
    @Column(name = "body",columnDefinition = "TEXT",nullable = true)
    private String body;
    @Column(name = "stack_trace", columnDefinition = "TEXT")
    private String stackTrace;

    @Column(name = "endpoint", length = 255)
    private String endpoint;

    @Column(name = "method", length = 10)
    private String method;
    @Column(name = "params",nullable = true)
    private String params;

}
