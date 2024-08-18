package com.lz.vulapp.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.lz.vulapp.entity.Vul;
import java.util.List;
import java.util.Map;

/**
* @author liangzai
* @description 针对表【vul】的数据库操作Mapper
* @createDate 2024-07-25 22:02:28
* @Entity com.lz.vulapp.entity.Vul
*/
public interface VulMapper extends MPJBaseMapper<Vul> {
    Vul findByNameAndIp(String name, String ip);

    List<Map<String, Object>> getLevelCounts();

    List<Map<String, Object>> getStatusCounts();

    List<Map<String, Object>> getVulCounts();

    List<Vul> getAllVuls();
}




