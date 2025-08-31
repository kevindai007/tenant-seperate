package com.kevindai.base.tenantseparate.multitenancy;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "multitenancy")
public class MultiTenancyProperties {

    private String defaultSchema;
    private MultiTenancyStrategy strategy = MultiTenancyStrategy.SCHEMA;
    private Map<String, DataSourceConfig> databases = new HashMap<>();


    @Getter
    @Setter
    public static class DataSourceConfig {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
        private Integer minimumIdle;
        private Integer maximumPoolSize;
        private Long idleTimeout;
        private Long maxLifetime;
        private Long connectionTimeout;
    }
}
