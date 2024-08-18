package com.lz.vulapp.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lz.vulapp.common.PlainResult;
import com.lz.vulapp.dto.AssetDto;
import com.lz.vulapp.dto.AssetExcelDto;
import com.lz.vulapp.entity.Asset;
import com.lz.vulapp.listener.AssetImportListener;
import com.lz.vulapp.service.AssetService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {
    @Resource
    private AssetService assetService;

    @GetMapping("")
    public PlainResult<IPage<AssetDto>> getAllAssets(@RequestParam(required = false) Integer pageNum,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) String name,
                                                     @RequestParam(required = false) String ip,
                                                     @RequestParam(required = false) String systemName,
                                                     @RequestParam(required = false) String worth) {
        IPage<AssetDto> assets = assetService.getAllAssets(pageNum, pageSize, name, ip, systemName,worth);
        return PlainResult.success(assets);
    }
    @PostMapping("/add")

    public PlainResult<String> addAsset(@RequestBody AssetDto assetDto) {
        assetService.addAsset(assetDto);
        return PlainResult.success("success");
    }

    @PostMapping("/{id}")
    public PlainResult<String> updateAsset(@RequestBody AssetDto assetDto) {
        assetService.updateAsset(assetDto);
        return PlainResult.success("success");
    }

    @DeleteMapping("/{id}")
    public PlainResult<String> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return PlainResult.success("success");
    }

    @PostMapping("/deleteBatch")
    public PlainResult<String> deleteAsset(@RequestBody List<Long> ids) {
        assetService.batchDeleteAsset(ids);
        return PlainResult.success("success");
    }

    @PostMapping("/import")
    public PlainResult<String> upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), AssetExcelDto.class, new AssetImportListener(assetService)).sheet().headRowNumber(1).doRead();
        return PlainResult.success("success");
    }
}
