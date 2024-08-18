package com.lz.vulapp.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.ConverterUtils;
import com.alibaba.excel.util.ListUtils;
import com.lz.vulapp.dto.VulExcelDto;
import com.lz.vulapp.entity.Vul;
import com.lz.vulapp.service.VulService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.excel.read.listener.ReadListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class VulImportListener implements ReadListener<VulExcelDto>  {
    private VulService vulService;
    private List<VulExcelDto> vulExcelDTOList = ListUtils.newArrayList();
    private final List<Vul> vulList = ListUtils.newArrayList();
    private int count = 0;

    public VulImportListener() {
    }

    public VulImportListener(VulService vulService) {
        this.vulService = vulService;
    }

    @Override
    public void invoke(VulExcelDto vulExcelDTO, AnalysisContext analysisContext) {
        log.info("解析到第 {} 条数据:{}", (++count), JSON.toJSONString(vulExcelDTO));
        vulExcelDTOList.add(vulExcelDTO);
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
        vulExcelDTOList.forEach(item -> {
            Vul vul = new Vul();
            vul.setName(item.getName());
            vul.setDetail(item.getDetail());
            vul.setIp(item.getIp());
            vul.setLocation(item.getLocation());
            vul.setSolution(item.getSolution());
            vul.setLevel(item.getLevel());
            vul.setStatus(item.getStatus());
            vul.setCve(item.getCve());
            vulList.add(vul);
        });
        vulExcelDTOList = ListUtils.newArrayList();
        vulService.batchInsert(vulList);
    }
}
