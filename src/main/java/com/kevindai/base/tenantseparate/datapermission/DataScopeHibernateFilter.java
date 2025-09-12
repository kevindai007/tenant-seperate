package com.kevindai.base.tenantseparate.datapermission;

import java.util.List;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DataScopeHibernateFilter {

    private final EntityManager entityManager;
    private final DataScopeService dataScopeService;

    //    @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    @Around(
            "execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..)) && " +
                    "!target(com.kevindai.base.tenantseparate.repository.RoleDataScopeRepository) && " +
                    "!target(com.kevindai.base.tenantseparate.repository.UserEntityRepository) && " +
                    "!target(com.kevindai.base.tenantseparate.repository.EntityClosureRepository)"
    )
    public Object applyDataScopeFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        UserDataScope scope = dataScopeService.currentScope();

        if (scope != null) {
            Session session = entityManager.unwrap(Session.class);
            session.enableFilter("dataScope")
                    .setParameter("isAll", scope.isAll())
                    .setParameterList("userIds", scope.getUserIds().isEmpty() ? List.of(-1L) : scope.getUserIds())
                    .setParameterList("entityIds", scope.getEntityIds().isEmpty() ? List.of(-1L) : scope.getEntityIds());

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

