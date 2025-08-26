package com.kevindai.base.tenantseparate.repository;

import com.kevindai.base.tenantseparate.entity.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsEntityRepository extends JpaRepository<GoodsEntity, Long> {
}