package org.ingredients.springdishmanager.repository;

import org.ingredients.springdishmanager.model.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DishRepository {

    private final DataSource dataSource;

    public DishRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Dish> findAll() {

        String sqlDish = "SELECT id, name, selling_price, dish_type FROM dish";
        List<Dish> dishes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement psDish = conn.prepareStatement(sqlDish);
             ResultSet rsDish = psDish.executeQuery()) {

            while (rsDish.next()) {

                Integer dishId = rsDish.getInt("id");

                List<DishIngredient> dishIngredients = findIngredientsByDishId(conn, dishId);

                Dish dish = Dish.builder()
                        .id(dishId)
                        .name(rsDish.getString("name"))
                        .price(rsDish.getDouble("selling_price"))
                        .dishIngredients(dishIngredients)
                        .build();

                dishes.add(dish);
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error while fetching dishes", e);
        }

        return dishes;
    }

    public Optional<Dish> findById(Integer id) {

        String sql = "SELECT id, name, selling_price FROM dish WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    List<DishIngredient> ingredients = findIngredientsByDishId(conn, id);

                    Dish dish = Dish.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .price(rs.getDouble("selling_price"))
                            .dishIngredients(ingredients)
                            .build();

                    return Optional.of(dish);
                }

                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error while fetching dish by id", e);
        }
    }

    public void removeAllIngredientsFromDish(Integer dishId) {

        String sql = "DELETE FROM dish_ingredient WHERE id_dish = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dishId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB error while deleting dish ingredients", e);
        }
    }

    public void addIngredientToDish(Integer dishId, Integer ingredientId) {

        String sql = "INSERT INTO dish_ingredient(id_dish, id_ingredient) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dishId);
            ps.setInt(2, ingredientId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB error while adding ingredient to dish", e);
        }
    }

    public boolean ingredientExists(Integer ingredientId) {

        String sql = "SELECT COUNT(id) FROM ingredient WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ingredientId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error while checking ingredient existence", e);
        }
    }

    private List<DishIngredient> findIngredientsByDishId(Connection conn, Integer dishId) throws SQLException {

        String sql = """
                SELECT i.id, i.name, i.category, i.price
                FROM dish_ingredient di
                JOIN ingredient i ON di.id_ingredient = i.id
                WHERE di.id_dish = ?
                """;

        List<DishIngredient> dishIngredients = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dishId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Ingredient ingredient = Ingredient.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .category(CategoryEnum.valueOf(rs.getString("category")))
                            .price(rs.getDouble("price"))
                            .build();

                    dishIngredients.add(
                            DishIngredient.builder()
                                    .ingredient(ingredient)
                                    .quantity(1.0)
                                    .build()
                    );
                }
            }
        }

        return dishIngredients;
    }
}