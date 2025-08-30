package com.kevindai.base.tenantseparate.dto;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kevindai.base.tenantseparate.entity.GoodsEntity;
import lombok.Data;

/**
 * DTO for {@link GoodsEntity}
 */

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class PriceEntityDto implements Serializable {

    Long id;
    Double price;
    String goodsName;
    Instant createdAt;
    Instant updatedAt;
}