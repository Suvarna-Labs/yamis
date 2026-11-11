package org.suvarna.yamis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    @Value("${db.driver-class-name}")
    private String driverClassName;

    @Value("${db.connection-host}")
    private String host;

    @Value("${db.connection-port}")
    private int port;

    @Value("${db.connection-catalog}")
    private String catalog;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.vendor}")
    private String vendor;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(getConnectionUrl(vendor));
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    private String getConnectionUrl(String vendor) {
        if (vendor == null) {
            throw new IllegalArgumentException("Database vendor cannot be null");
        }

        return switch (vendor.trim().toLowerCase()) {
            case "mysql" -> "jdbc:mysql://" + host + ":" + port + "/" + catalog;
            case "postgresql" -> "jdbc:postgresql://" + host + ":" + port + "/" + catalog;
            default -> throw new IllegalArgumentException("Unsupported database vendor: " + vendor);
        };
    }
}

