package com.kevindai.base.tenantseparate.repository;

import java.util.List;

import com.kevindai.base.tenantseparate.entity.RoleDataScopeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleDataScopeRepository extends JpaRepository<RoleDataScopeEntity, Long> {

    @Query(
            """
                    select rds from RoleDataScopeEntity rds
                    inner join UserRoleEntity ure on rds.roleId = ure.id.roleId
                    where ure.id.userId = ?1
                    """
    )
    List<RoleDataScopeEntity> findByUserId(Long userId);
}
