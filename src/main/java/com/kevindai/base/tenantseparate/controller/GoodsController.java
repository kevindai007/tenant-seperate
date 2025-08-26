package com.kevindai.base.tenantseparate.controller;

import com.kevindai.base.tenantseparate.dto.GoodsCreationDto;
import com.kevindai.base.tenantseparate.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/goods")
public class GoodsController {
    private final GoodsService goodsService;

    @PostMapping
    public void create(@RequestBody GoodsCreationDto goodsCreationDto) {
        goodsService.create(goodsCreationDto);
    }

    @GetMapping
    public Object list() {
        return goodsService.findAll();
    }
}
