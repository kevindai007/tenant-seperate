package com.kevindai.base.tenantseparate.repository;

import java.util.List;

import com.kevindai.base.tenantseparate.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PriceEntityRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p inner join GoodsEntity g on p.goodsName = g.goodsName WHERE g.goodsName = ?1")
    List<PriceEntity> findByGoodsName(String goodsName);

}