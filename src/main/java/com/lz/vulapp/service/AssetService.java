package com.lz.vulapp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lz.vulapp.dto.AssetDto;
import com.lz.vulapp.entity.Asset;
import java.util.List;

public interface AssetService {

    IPage<AssetDto> getAllAssets(Integer pageNum, Integer pageSize,String name, String ip, String systemName,  String worth);

    void addAsset(AssetDto assetDto);

    void updateAsset(AssetDto assetDto);

    void deleteAsset(Long id);

    void batchDeleteAsset(List<Long> ids);

    void batchInsert(List<Asset> assetList);
}
