package com.lz.vulapp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.lz.vulapp.dto.VulDto;
import com.lz.vulapp.dto.VulTopDto;
import com.lz.vulapp.entity.Asset;
import com.lz.vulapp.entity.Vul;
import com.lz.vulapp.mapper.AssetMapper;
import com.lz.vulapp.mapper.VulMapper;
import com.lz.vulapp.service.VulService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VulServiceImpl implements VulService {
    @Resource
    private VulMapper vulMapper;

    @Resource
    private AssetMapper assetMapper;

    @Override
    public void addVul(VulDto vulDto) {
        Vul vul = convertToVul(vulDto);
        vul.setCreatedAt(LocalDateTime.now());
        vulMapper.insert(vul);
    }

    @Override
    public void updateVul(VulDto vulDto) {
        Vul vul = vulMapper.selectById(vulDto.getId());
        vul.setDetail(vulDto.getDetail());
        vul.setLocation(vulDto.getLocation());
        vul.setSolution(vulDto.getSolution());
        vul.setLevel(vulDto.getLevel());
        vul.setPriority(vulDto.getPriority());
        vul.setStatus(vulDto.getStatus());
        vul.setUpdatedAt(LocalDateTime.now());
        vulMapper.updateById(vul);
    }

    @Override
    public void deleteVul(Long id) {
        vulMapper.deleteById(id);
    }

    @Override
    public void batchDelete(List<Long> ids) {
        vulMapper.deleteBatchIds(ids);
    }

    @Override
    public IPage<VulDto> getAllVuls(Integer pageNum, Integer pageSize, String name, String ip, String level, String priority, String status, String startTime, String endTime) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 20;
        }
        MPJLambdaWrapper<Vul> wrappers = new MPJLambdaWrapper<Vul>()
                .selectAll(Vul.class)
                .select(Asset::getSystemName)
                .leftJoin(Asset.class, Asset::getIp, Vul::getIp);
        if (StringUtils.hasText(name)) {
            wrappers.like(Vul::getName,name);
        }
        if (StringUtils.hasText(ip)) {
            wrappers.eq(Vul::getIp,ip);
        }
        if (StringUtils.hasText(level)) {
            wrappers.eq(Vul::getLevel, level);
        }
        if (StringUtils.hasText(priority)) {
            wrappers.eq(Vul::getPriority, priority);
        }
        if (StringUtils.hasText(status)) {
            wrappers.eq(Vul::getStatus, status);
        }
        if(startTime!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime sTime = LocalDateTime.parse(startTime, formatter);
            LocalDateTime eTime = LocalDateTime.parse(endTime, formatter);
            wrappers.between(Vul::getCreatedAt, sTime, eTime);
        }
        Page<VulDto> vulPage =vulMapper.selectJoinPage(new Page<>(pageNum, pageSize), VulDto.class,  wrappers);

        return vulPage;
    }

    @Override
    public void batchInsert(List<Vul> vulList) {
        vulList.forEach(this::processVul);
    }
    public void processVul(Vul vul) {
        Vul existingVul = vulMapper.findByNameAndIp(vul.getName(), vul.getIp());
        if (existingVul != null) {
            if (!Objects.equals(existingVul.getStatus(), "accept") && !Objects.equals(existingVul.getStatus(), "mis")) {
                existingVul.setStatus("unfix");
            }
            existingVul.setUpdatedAt(LocalDateTime.now());
            vulMapper.updateById(existingVul);
        } else {
            vulMapper.insert(vul);
        }
    }

    private static Vul convertToVul(VulDto vulDto) {
        Vul vul = new Vul();
        vul.setId(vulDto.getId());
        vul.setName(vulDto.getName());
        vul.setIp(vulDto.getIp());
        vul.setLocation(vulDto.getLocation());
        vul.setSolution(vulDto.getSolution());
        vul.setDetail(vulDto.getDetail());
        vul.setLevel(vulDto.getLevel());
        vul.setPriority(vulDto.getPriority());
        vul.setStatus(vulDto.getStatus());
        vul.setCve(vulDto.getCve());
        return vul;
    }
    @Override
    public Map<String, Long> getDataCounts(){
        List<Map<String, Object>> levelCounts = vulMapper.getLevelCounts();
        List<Map<String, Object>> statusCounts = vulMapper.getStatusCounts();
        Map<String, Long> dataCountMap = new HashMap<>();
        for (Map<String, Object> levelCount : levelCounts) {
            String level = (String) levelCount.get("level");
            long count = (long) levelCount.get("count");
            dataCountMap.put(level, count);
        }
        for (Map<String, Object> statusCount : statusCounts) {
            String status = (String) statusCount.get("status");
            long count = (long) statusCount.get("count");
            dataCountMap.put(status, count);
        }
        return dataCountMap;
    }

    @Override
    public List<VulTopDto> getTopVulCounts() {
        List<Map<String, Object>> vulCounts = vulMapper.getVulCounts();
        List<VulTopDto> topVulCounts = new ArrayList<>();
        for (int i = 0; i < Math.min(10, vulCounts.size()); i++) {
            Map<String, Object> vulCount = vulCounts.get(i);
            VulTopDto vulTopDto = new VulTopDto();
            vulTopDto.setName((String) vulCount.get("name"));
            vulTopDto.setCount((long) vulCount.get("count"));
            topVulCounts.add(vulTopDto);
        }
        return topVulCounts;
    }

    @Override
    public int coutPriority() {
        List<Vul> vulList = vulMapper.getAllVuls();
        int cout =0;
        for (Vul vul : vulList){
            Asset asset = assetMapper.findByIp(vul.getIp());
            if(asset !=null){
                int level = getLevelValue(vul.getLevel());
                int worth = Integer.parseInt(asset.getWorth());
                int priority = level+worth;
                if(priority >5){
                    vul.setPriority("3");
                }else if (priority <4){
                    vul.setPriority("1");
                }else {
                    vul.setPriority("2");
                }
                vulMapper.updateById(vul);
                cout++;
            }
        }
        return cout;
    }
    private int getLevelValue(String level){
        switch (level){
            case "critical":
                return 4;
            case "high":
                return 3;
            case "medium":
                return 2;
            case "low":
                return 1;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }
    }

    @Override
    public void batchEditStatus(List<Long> ids,String newStatus) {
        for (Long id : ids) {
            Vul vul = vulMapper.selectById(id);
            vul.setStatus(newStatus);
            vulMapper.updateById(vul);
        }
    }
}
