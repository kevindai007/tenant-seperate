package com.kevindai.base.tenantseparate.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_role", schema = "r100_jane")
public class UserRoleEntity {

    @EmbeddedId
    private UserRoleEntityId id;
}