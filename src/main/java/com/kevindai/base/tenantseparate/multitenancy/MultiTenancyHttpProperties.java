package com.kevindai.base.tenantseparate.multitenancy;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "multitenancy.http")
public class MultiTenancyHttpProperties {
    private String headerName;
}
