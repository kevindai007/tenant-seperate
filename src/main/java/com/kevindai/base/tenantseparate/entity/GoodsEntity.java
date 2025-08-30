package com.kevindai.base.tenantseparate.entity;

import com.kevindai.base.tenantseparate.multitenancy.context.TenantContext;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.TenantId;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "goods")
public class GoodsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "goods_name", length = 256)
    private String goodsName;

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
