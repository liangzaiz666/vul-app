package com.lz.vulapp.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ConverterUtils;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.lz.vulapp.dto.AssetExcelDto;
import com.lz.vulapp.entity.Asset;
import com.lz.vulapp.service.AssetService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;
@Slf4j
public class AssetImportListener implements ReadListener<AssetExcelDto> {
    private AssetService assetService;
    private List<AssetExcelDto> assetExcelDtoList = ListUtils.newArrayList();
    private final List<Asset> assetList = ListUtils.newArrayList();
    private int count = 0;

    public AssetImportListener() {
    }

    public AssetImportListener(AssetService assetService) {
        this.assetService = assetService;
    }

    @Override
    public void invoke(AssetExcelDto assetExcelDto, AnalysisContext analysisContext) {
        log.info("解析到第 {} 条数据:{}", (++count), JSON.toJSONString(assetExcelDto));
        assetExcelDtoList.add(assetExcelDto);
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        Map<Integer, String> stringMap = ConverterUtils.convertToStringMap(headMap, context);
        Set<Integer> keySet = stringMap.keySet();
        System.out.println("该Excel表头信息是：");
        for (int i = 0; i < keySet.size(); i++) {
            System.out.println("第 " + (i + 1) + " 列 = " + stringMap.get(i));
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("该Excel的所有数据解析完成！！！");
        assetExcelDtoList.forEach(item -> {
            Asset asset = new Asset();
            asset.setName(item.getName());
            asset.setIp(item.getIp());
            asset.setSystemName(item.getSystemName());
            asset.setWorth(item.getWorth());
            assetList.add(asset);
        });
        assetExcelDtoList = ListUtils.newArrayList();
        assetService.batchInsert(assetList);
    }
}
