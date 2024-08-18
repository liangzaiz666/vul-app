package com.lz.vulapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VulDto {
    private Long id;
    private String name;
    private String detail;
    private String ip;
    private String location;
    private String solution;
    private String level;
    private String priority;
    private String status;
    private String cve;
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private String systemName;
}
