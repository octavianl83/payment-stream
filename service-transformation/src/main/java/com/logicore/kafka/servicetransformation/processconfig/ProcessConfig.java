package com.logicore.kafka.servicetransformation.processconfig;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProcessConfig {
    private String tenantId;
    private Integer version;
    private String config;
    private LocalDateTime date;

}