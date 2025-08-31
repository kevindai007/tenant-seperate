package com.kevindai.base.tenantseparate.data.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import com.kevindai.base.tenantseparate.multitenancy.MultiTenancyProperties;
import com.kevindai.base.tenantseparate.multitenancy.MultiTenancyStrategy;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.MultiTenancySettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ConnectionProvider implements MultiTenantConnectionProvider<String>, HibernatePropertiesCustomizer {

    private final DataSource dataSource;
    private final MultiTenancyProperties multiTenancyProperties;
    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();

    @PostConstruct
    public void initialize() {
        if (multiTenancyProperties.getStrategy() == MultiTenancyStrategy.DATABASE) {
            for (Map.Entry<String, MultiTenancyProperties.DataSourceConfig> entry : multiTenancyProperties.getDatabases().entrySet()) {
                MultiTenancyProperties.DataSourceConfig config = entry.getValue();
                HikariDataSource ds = DataSourceBuilder.create()
                        .type(HikariDataSource.class)
                        .url(config.getUrl())
                        .username(config.getUsername())
                        .password(config.getPassword())
                        .driverClassName(config.getDriverClassName())
                        .build();
                if (config.getMinimumIdle() != null) {
                    ds.setMinimumIdle(config.getMinimumIdle());
                }
                if (config.getMaximumPoolSize() != null) {
                    ds.setMaximumPoolSize(config.getMaximumPoolSize());
                }
                if (config.getIdleTimeout() != null) {
                    ds.setIdleTimeout(config.getIdleTimeout());
                }
                if (config.getMaxLifetime() != null) {
                    ds.setMaxLifetime(config.getMaxLifetime());
                }
                if (config.getConnectionTimeout() != null) {
                    ds.setConnectionTimeout(config.getConnectionTimeout());
                }
                dataSources.put(entry.getKey(), ds);
            }
        }
    }

    @PreDestroy
    public void destroy() {
        dataSources.values().forEach(this::closeDataSource);
    }

    public void removeDataSource(String tenantIdentifier) {
        DataSource ds = dataSources.remove(tenantIdentifier);
        if (ds != null) {
            closeDataSource(ds);
        }
    }

    private void closeDataSource(DataSource ds) {
        if (ds instanceof AutoCloseable closeable) {
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        if (multiTenancyProperties.getStrategy() == MultiTenancyStrategy.SCHEMA) {
            return getConnection(multiTenancyProperties.getDefaultSchema());
        }
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        if (multiTenancyProperties.getStrategy() == MultiTenancyStrategy.SCHEMA) {
            connection.setSchema(multiTenancyProperties.getDefaultSchema());
        }
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        if (multiTenancyProperties.getStrategy() == MultiTenancyStrategy.SCHEMA) {
            Connection connection = dataSource.getConnection();
            connection.setSchema(tenantIdentifier);
            return connection;
        }
        DataSource ds = dataSources.getOrDefault(tenantIdentifier, dataSource);
        return ds.getConnection();
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        if (multiTenancyProperties.getStrategy() == MultiTenancyStrategy.SCHEMA) {
            connection.setSchema(multiTenancyProperties.getDefaultSchema());
        }
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        if (multiTenancyProperties.getStrategy() == MultiTenancyStrategy.SCHEMA
                || multiTenancyProperties.getStrategy() == MultiTenancyStrategy.DATABASE) {
            hibernateProperties.put(MultiTenancySettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
        }
    }
}
