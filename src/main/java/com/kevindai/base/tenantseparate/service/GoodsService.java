package com.kevindai.base.tenantseparate.service;

import java.time.Instant;
import java.util.List;

import com.kevindai.base.tenantseparate.dto.GoodEntityDto;
import com.kevindai.base.tenantseparate.dto.GoodsCreationDto;
import com.kevindai.base.tenantseparate.entity.GoodsEntity;
import com.kevindai.base.tenantseparate.repository.GoodsEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GoodsService {

    private final GoodsEntityRepository goodsEntityRepository;

    public void create(GoodsCreationDto goodsCreationDto) {
        var entity = new GoodsEntity();
        entity.setGoodsName(goodsCreationDto.getName());
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        goodsEntityRepository.save(entity);
    }

    public List<GoodEntityDto> findAll() {
        return goodsEntityRepository.findAll().stream().map(entity -> {
            var dto = new GoodEntityDto();
            dto.setId(entity.getId());
            dto.setGoodsName(entity.getGoodsName());
            dto.setCreatedAt(entity.getCreatedAt());
            dto.setUpdatedAt(entity.getUpdatedAt());
            return dto;
        }).toList();
    }

    @Cacheable(value = "goodsByName",key = "#name")
    public GoodEntityDto getByName(String name) {
        var entity = goodsEntityRepository.findByGoodsName(name);
        if (entity == null) {
            return null;
        }
        var dto = new GoodEntityDto();
        dto.setId(entity.getId());
        dto.setGoodsName(entity.getGoodsName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;

    }
}
