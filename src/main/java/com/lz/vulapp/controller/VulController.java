package com.lz.vulapp.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lz.vulapp.common.PlainResult;
import com.lz.vulapp.dto.VulDto;
import com.lz.vulapp.dto.VulExcelDto;
import com.lz.vulapp.dto.VulTopDto;
import com.lz.vulapp.service.VulService;
import com.lz.vulapp.listener.VulImportListener;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vuls")
public class VulController {
    @Resource
    private VulService vulService;

    @GetMapping("")
    public PlainResult<IPage<VulDto>> getAllVuls(@RequestParam(required = false) Integer pageNum,
                                              @RequestParam(required = false) Integer pageSize,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) String ip,
                                              @RequestParam(required = false) String level,
                                              @RequestParam(required = false) String priority,
                                              @RequestParam(required = false) String status,
                                              @RequestParam(required = false) String startTime ,
                                              @RequestParam(required = false) String endTime) {
        IPage<VulDto> vuls = vulService.getAllVuls(pageNum, pageSize, name, ip, level, priority, status, startTime, endTime);
        return PlainResult.success(vuls);
    }
    @PostMapping("/add")

    public PlainResult<String> addVul(@RequestBody VulDto vulDto) {
        vulService.addVul(vulDto);
        return PlainResult.success("success");
    }

    @PutMapping("/{id}")
    public PlainResult<String> updateVul(@RequestBody VulDto vulDto) {
        vulService.updateVul(vulDto);
        return PlainResult.success("success");
    }

    @DeleteMapping("/{id}")
    public PlainResult<String> deleteVul(@PathVariable Long id) {
        vulService.deleteVul(id);
        return PlainResult.success("success");
    }

    @PostMapping("/deleteBatch")
    public PlainResult<String> deleteBatch(@RequestBody List<Long> ids) {
        vulService.batchDelete(ids);
        return PlainResult.success("success");
    }


    @PostMapping("/import")
    public PlainResult<String> upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), VulExcelDto.class, new VulImportListener(vulService)).sheet().headRowNumber(1).doRead();
        return PlainResult.success("success");
    }

    @GetMapping("/dataCount")
    public PlainResult<Map<String, Long>> getDataCounts() {
        return PlainResult.success(vulService.getDataCounts());
    }

    @GetMapping("/topCount")
    public PlainResult<List<VulTopDto>> getTopCount() {
        return PlainResult.success(vulService.getTopVulCounts());
    }

    @GetMapping("/coutPriority")
    public PlainResult<Integer> coutPriority() {
        int count = vulService.coutPriority();
        return PlainResult.success(count);
    }

    @PostMapping("/batchEditStatus")
    public PlainResult<String> batchEditStatus(@RequestBody List<Long> ids, @RequestParam String newStatus) {
        vulService.batchEditStatus(ids, newStatus);
        return PlainResult.success("success");
    }
}
