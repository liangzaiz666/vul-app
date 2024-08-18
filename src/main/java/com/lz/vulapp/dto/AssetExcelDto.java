package com.lz.vulapp.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetExcelDto {
    @ExcelProperty(index = 0)
    private Integer id;

    @ExcelProperty(index = 1)
    private String name;

    @ExcelProperty(index = 2)
    private String ip;

    @ExcelProperty(index = 3)
    private String systemName;

    @ExcelProperty(index = 4)
    private String worth;
}
