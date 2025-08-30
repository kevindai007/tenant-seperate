package com.kevindai.base.tenantseparate.data.hibernate;

import java.util.Map;
import java.util.Objects;

import com.kevindai.base.tenantseparate.multitenancy.MultiTenancyProperties;
import com.kevindai.base.tenantseparate.multitenancy.context.TenantContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {

    private final MultiTenancyProperties multiTenancyProperties;

    @Override
    public Object resolveCurrentTenantIdentifier() {
        return Objects.requireNonNullElse(TenantContext.getTenantId(), multiTenancyProperties.getDefaultSchema());
    }

    @Override
    public boolean validateExistingCurrentSessions() {

        return false;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
        hibernateProperties.put(AvailableSettings.MULTI_TENANT, multiTenancyProperties.getStrategy());
    }
}
