package com.kevindai.base.tenantseparate.multitenancy.resolver;

import org.springframework.lang.NonNull;

@FunctionalInterface
public interface TenantResolver<T> {
    String resolveTenantIdentifier(@NonNull T contextData);
}
