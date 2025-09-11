package com.kevindai.base.tenantseparate.datapermission;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DataScopeHibernateFilter {
    private final EntityManager entityManager;
    private final DataScopeService dataScopeService;

    @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    public Object applyDataScopeFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        UserDataScope scope = dataScopeService.currentScope();
        
        if (scope != null) {
            log.info("Applying dataScope filter with isAll={}, userIds={}, entityIds={}", 
                     scope.isAll(), scope.getUserIds(), scope.getEntityIds());
                     
            Session session = entityManager.unwrap(Session.class);
            session.enableFilter("dataScope")
                    .setParameter("isAll", scope.isAll())
                    .setParameterList("userIds", scope.getUserIds().isEmpty()? List.of(-1L): scope.getUserIds())
                    .setParameterList("entityIds", scope.getEntityIds().isEmpty()? List.of(-1L): scope.getEntityIds());
            
            try {
                return joinPoint.proceed();
            } finally {
                session.disableFilter("dataScope");
                log.info("DataScope filter disabled");
            }
        } else {
            log.warn("UserDataScope is null, filter not applied");
            return joinPoint.proceed();
        }
    }
}

