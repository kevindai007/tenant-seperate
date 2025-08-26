package com.kevindai.base.tenantseparate.multitenancy.resolver;

import com.kevindai.base.tenantseparate.multitenancy.MultiTenancyHttpProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest> {
    private final MultiTenancyHttpProperties httpProperties;

    @Override
    public String resolveTenantIdentifier(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(httpProperties.getHeaderName());
    }
}
