package com.kevindai.base.tenantseparate.repository;

import java.util.List;

import com.kevindai.base.tenantseparate.entity.UserEntityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntityEntity, Long> {

    List<UserEntityEntity> findByUserId(Long userId);
}
