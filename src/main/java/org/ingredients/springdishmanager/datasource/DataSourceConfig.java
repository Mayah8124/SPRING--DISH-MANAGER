package org.ingredients.springdishmanager.datasource;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

import java.sql.Connection;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/dish_management_db")
                .username("dish_manager_user")
                .password("123456")
                .build();
    }

    @Bean
    public CommandLineRunner testConnection(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                System.out.println(" DB connected");
            } catch (Exception e) {
                System.err.println("DB error: " + e.getMessage());
            }
        };
    }
}