package org.ingredients.springdishmanager.repository;


import org.ingredients.springdishmanager.model.CategoryEnum;
import org.ingredients.springdishmanager.model.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    public IngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ingredient> findAll() {
        String sql = "SELECT id, name, category, price FROM ingredient";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                Ingredient.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .category(CategoryEnum.valueOf(rs.getString("category")))
                        .price(rs.getDouble("price"))
                        .build()
        );
    }

    public Optional<Ingredient> findById(Integer id) {
        String sql = "SELECT id, name, category, price FROM ingredient WHERE id = ?";

        List<Ingredient> results = jdbcTemplate.query(sql,
                new Object[]{id},
                (rs, rowNum) ->
                        Ingredient.builder()
                                .id(rs.getInt("id"))
                                .name(rs.getString("name"))
                                .category(CategoryEnum.valueOf(rs.getString("category")))
                                .price(rs.getDouble("price"))
                                .build()
        );

        return results.stream().findFirst();
    }
}
