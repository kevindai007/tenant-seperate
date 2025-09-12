package com.kevindai.base.tenantseparate.repository;

import java.util.List;
import java.util.Set;

import com.kevindai.base.tenantseparate.entity.EntityClosureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EntityClosureRepository extends JpaRepository<EntityClosureEntity, Long> {

    @Query("""
            select ecc.descendant.id from EntityClosureEntity ecc
            where ecc.ancestor.id in ?1
            """)
    Set<Long> findEntityAndSubByAncestorIds(List<Long> ancestorIds);



}
