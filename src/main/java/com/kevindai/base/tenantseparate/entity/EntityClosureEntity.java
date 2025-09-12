package com.kevindai.base.tenantseparate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "entity_closure", schema = "r100_jane")
public class EntityClosureEntity {

    @EmbeddedId
    private EntityClosureEntityId id;

    @MapsId("ancestorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ancestor_id", nullable = false)
    private EntityInfoEntity ancestor;

    @MapsId("descendantId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "descendant_id", nullable = false)
    private EntityInfoEntity descendant;

    @Column(name = "depth", nullable = false)
    private Integer depth;

}