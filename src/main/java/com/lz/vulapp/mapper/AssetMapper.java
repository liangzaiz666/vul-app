package com.lz.vulapp.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.lz.vulapp.entity.Asset;

/**
* @author liangzai
* @description 针对表【asset】的数据库操作Mapper
* @createDate 2024-07-24 22:46:57
* @Entity com.lz.vulapp.entity.Asset
*/
public interface AssetMapper extends MPJBaseMapper<Asset> {
    Asset findByIp(String ip);
}




