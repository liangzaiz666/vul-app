package com.lz.vulapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.vulapp.dto.AssetDto;
import com.lz.vulapp.entity.Asset;
import com.lz.vulapp.mapper.AssetMapper;
import com.lz.vulapp.service.AssetService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;

@Service

public class AssetServiceImpl implements AssetService {
    @Resource
    private AssetMapper assetMapper;
    @Override
    public IPage<AssetDto> getAllAssets(Integer pageNum, Integer pageSize,String name, String ip, String systemName, String worth) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        LambdaQueryWrapper<Asset> wrappers = Wrappers.lambdaQuery();
        if (StringUtils.hasText(name)) {
            wrappers.like(Asset::getName,name);
        }
        if (StringUtils.hasText(ip)) {
            wrappers.like(Asset::getIp,ip);
        }
        if (StringUtils.hasText(systemName)) {
            wrappers.eq(Asset::getSystemName, systemName);
        }
        if (StringUtils.hasText(worth)) {
            wrappers.eq(Asset::getWorth, worth);
        }
        wrappers.orderByDesc(Asset::getUpdatedAt);
        Page<Asset> assetPage = assetMapper.selectPage(new Page<>(pageNum, pageSize), wrappers);
        if (assetPage == null) {
            return null;
        }
        return assetPage.convert(this::toAssetDto);
    }
    private AssetDto toAssetDto(Asset asset) {
        AssetDto assetDto = new AssetDto();
        assetDto.setId(asset.getId());
        assetDto.setName(asset.getName());
        assetDto.setIp(asset.getIp());
        assetDto.setSystemName(asset.getSystemName());
        assetDto.setWorth(asset.getWorth());
        assetDto.setCreatedAt(asset.getCreatedAt());
        assetDto.setUpdatedAt(asset.getUpdatedAt());
        return assetDto;
    }

    @Override
    public void addAsset(AssetDto assetDto) {
        Asset asset = convertToAsset(assetDto);
        asset.setCreatedAt(LocalDateTime.now());
        assetMapper.insert(asset);
    }
    private static Asset convertToAsset(AssetDto assetDto) {
        Asset asset = new Asset();
        asset.setId(assetDto.getId());
        asset.setName(assetDto.getName());
        asset.setIp(assetDto.getIp());
        asset.setSystemName(assetDto.getSystemName());
        asset.setWorth(assetDto.getWorth());
        return asset;
    }

    @Override
    public void updateAsset(AssetDto assetDto) {
        Asset asset = assetMapper.selectById(assetDto.getId());
        asset.setIp(assetDto.getIp());
        asset.setSystemName(assetDto.getSystemName());
        asset.setWorth(assetDto.getWorth());
        asset.setUpdatedAt(LocalDateTime.now());
        assetMapper.updateById(asset);

    }

    @Override
    public void deleteAsset(Long id) {
        assetMapper.deleteById(id);
    }

    @Override
    public void batchDeleteAsset(List<Long> ids) {
        assetMapper.deleteBatchIds(ids);
    }

    @Override
    public void batchInsert(List<Asset> assetList) {
        assetList.forEach(this::processVul);
    }

    public void processVul(Asset asset) {
        Asset existingAsset = assetMapper.findByIp(asset.getIp());
        if (existingAsset != null) {
            existingAsset.setUpdatedAt(LocalDateTime.now());
            assetMapper.updateById(existingAsset);
        } else {
            assetMapper.insert(asset);
        }
    }
}
