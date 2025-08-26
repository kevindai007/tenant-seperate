package com.kevindai.base.tenantseparate.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kevindai.base.tenantseparate.entity.GoodsEntity;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link GoodsEntity}
 */

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class GoodEntityDto implements Serializable {
    Long id;
    String goodsName;
    Instant createdAt;
    Instant updatedAt;
}