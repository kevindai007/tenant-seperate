package com.kevindai.base.tenantseparate.multitenancy.context;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TenantContext {
    private static final ThreadLocal<String> CONTEXT = new InheritableThreadLocal<>();

    public static void setTenantId(String tenantId) {
        CONTEXT.set(tenantId);
        log.debug("Set tenant id to {}", tenantId);
    }

    public static String getTenantId() {
        return CONTEXT.get();
    }
    public static void clear() {
        CONTEXT.remove();
        log.debug("Cleared tenant id");
    }
}
