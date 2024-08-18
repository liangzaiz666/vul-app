package com.lz.vulapp.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VulExcelDto {
    @ExcelProperty(index = 0)
    private Integer id;

    @ExcelProperty(index = 1)
    private String name;

    @ExcelProperty(index = 2)
    private String detail;

    @ExcelProperty(index = 3)
    private String ip;

    @ExcelProperty(index = 4)
    private String location;

    @ExcelProperty(index = 5)
    private String solution;

    @ExcelProperty(index = 6)
    private String level;

    @ExcelProperty(index = 7)
    private String status;

    @ExcelProperty(index = 8)
    private String cve;

    @ExcelProperty(index = 9)
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private String createdAt;
}
