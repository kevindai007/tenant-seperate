package com.kevindai.base.tenantseparate.controller;

import com.kevindai.base.tenantseparate.dto.GoodEntityDto;
import com.kevindai.base.tenantseparate.dto.GoodsCreationDto;
import com.kevindai.base.tenantseparate.dto.PriceEntityDto;
import com.kevindai.base.tenantseparate.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/name/{name}")
    public GoodEntityDto getByName(@PathVariable String name) {
        return goodsService.getByName(name);
    }

    @PostMapping("/price")
    public void setPrice(@RequestBody PriceEntityDto priceEntityDto) {
        goodsService.createPrice(priceEntityDto);
    }

    @GetMapping("/price/{name}")
    public PriceEntityDto getPriceByName(@PathVariable String name) {
        return goodsService.getPriceByName(name);
    }
}
