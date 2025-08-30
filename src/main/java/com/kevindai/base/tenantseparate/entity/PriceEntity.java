package com.kevindai.base.tenantseparate.entity;

import java.time.Instant;

import com.kevindai.base.tenantseparate.multitenancy.context.TenantContext;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.TenantId;

@Getter
@Setter
@Entity
@Table(name = "price")
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "goods_name", length = 256)
    private String goodsName;

    @Column(name = "price", nullable = false)
    private Double price;

    // Marked with @TenantId so Hibernate automatically restricts queries by tenant
    @TenantId
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("now()")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        tenantId = TenantContext.getTenantId();
    }
}
