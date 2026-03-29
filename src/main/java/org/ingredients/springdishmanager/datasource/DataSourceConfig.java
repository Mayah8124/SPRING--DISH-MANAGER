package org.ingredients.springdishmanager.datasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
public class DataSourceConfig {

    public DataSource createDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/dish_management_db")
                .username("dish_manager_user")
                .password("123456")
                .build();
    }

    public void testConnection() {
        try (Connection conn = createDataSource().getConnection()) {
            System.out.println("connected successfully !");
        } catch (SQLException e) {
            System.err.println("error while connecting : " + e.getMessage());
        }
    }

}