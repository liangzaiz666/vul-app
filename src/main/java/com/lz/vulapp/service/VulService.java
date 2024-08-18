package com.lz.vulapp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lz.vulapp.dto.VulDto;
import com.lz.vulapp.dto.VulTopDto;
import com.lz.vulapp.entity.Vul;
import java.util.List;
import java.util.Map;

public interface VulService {
    void addVul(VulDto vulDto);

    void updateVul(VulDto vulDto);

    void deleteVul(Long id);

    void batchDelete(List<Long> ids);

    IPage<VulDto> getAllVuls(Integer pageNum, Integer pageSize, String name, String ip, String level, String priority, String status, String startTime, String endTime);

    void batchInsert(List<Vul> vulList);

    Map<String, Long> getDataCounts();

    List<VulTopDto> getTopVulCounts();

    int coutPriority();

    void batchEditStatus(List<Long> ids, String newStatus);
}
