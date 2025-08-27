package com.kevindai.base.tenantseparate.multitenancy.resolver;

import java.lang.reflect.Method;

import com.kevindai.base.tenantseparate.multitenancy.context.TenantContext;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.stereotype.Component;

@Component
public class TenantKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return SimpleKeyGenerator.generateKey(TenantContext.getTenantId(), params);
    }
}
