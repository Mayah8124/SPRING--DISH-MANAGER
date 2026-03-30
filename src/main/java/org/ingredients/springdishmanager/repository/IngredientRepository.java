package org.ingredients.springdishmanager.repository;

import org.ingredients.springdishmanager.model.CategoryEnum;
import org.ingredients.springdishmanager.model.Ingredient;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class IngredientRepository {

    private final DataSource dataSource;

    public IngredientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Ingredient> findAll() {

        String sql = "SELECT id, name, category, price FROM ingredient";
        List<Ingredient> ingredients = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ingredients.add(mapIngredient(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error while fetching ingredients", e);
        }

        return ingredients;
    }

    public Optional<Ingredient> findById(Integer id) {

        String sql = "SELECT id, name, category, price FROM ingredient WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapIngredient(rs));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error while fetching ingredient by id", e);
        }
    }

    private Ingredient mapIngredient(ResultSet rs) throws SQLException {
        return Ingredient.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .category(CategoryEnum.valueOf(rs.getString("category")))
                .price(rs.getDouble("price"))
                .build();
    }
}