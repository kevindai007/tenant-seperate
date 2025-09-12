package com.kevindai.base.tenantseparate.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Embeddable
public class EntityClosureEntityId implements Serializable {

    private static final long serialVersionUID = -5844851414779131892L;
    @Column(name = "ancestor_id", nullable = false)
    private Long ancestorId;

    @Column(name = "descendant_id", nullable = false)
    private Long descendantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        EntityClosureEntityId entity = (EntityClosureEntityId) o;
        return Objects.equals(this.ancestorId, entity.ancestorId) &&
                Objects.equals(this.descendantId, entity.descendantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ancestorId, descendantId);
    }

}