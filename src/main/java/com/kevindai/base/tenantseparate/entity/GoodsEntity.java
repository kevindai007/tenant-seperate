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
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.TenantId;

@FilterDef(name = "dataScope", parameters = {
        @ParamDef(name = "isAll", type = java.lang.Boolean.class),
        @ParamDef(name = "userIds", type = java.lang.Long.class),
        @ParamDef(name = "entityIds", type = java.lang.Long.class)
})
@Filter(
        name = "dataScope",
        condition = "(:isAll = true) or (owner_id in (:userIds)) or (owing_entity_id in (:entityIds))"
)
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

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "owing_entity_id")
    private Long owingEntityId;

    @PrePersist
    public void prePersist() {
        tenantId = TenantContext.getTenantId();
    }
}
