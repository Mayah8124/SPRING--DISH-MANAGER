package org.ingredients.springdishmanager.repository;

import org.ingredients.springdishmanager.model.CategoryEnum;
import org.ingredients.springdishmanager.model.Dish;
import org.ingredients.springdishmanager.model.DishIngredient;
import org.ingredients.springdishmanager.model.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DishRepository {
    private final JdbcTemplate jdbcTemplate;

    public DishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Dish> findAll() {
        String sql = "SELECT id, name, selling_price, dish_type FROM dish";

        List<Dish> dishes = jdbcTemplate.query(sql, (rs, rowNum) -> {
            int dishId = rs.getInt("id");
            String name = rs.getString("name");
            Double price = rs.getDouble("price");

            List<DishIngredient> dishIngredients = jdbcTemplate.query(
                    "SELECT di.id AS dish_ingredient_id, i.id AS ingredient_id, i.name, i.category, i.price " +
                            "FROM dish_ingredient di " +
                            "JOIN ingredient i ON di.id_ingredient = i.id " +
                            "WHERE di.id_dish = ?",
                    new Object[]{dishId},
                    (rs2, rowNum2) -> {
                        Ingredient ingredient = Ingredient.builder()
                                .id(rs2.getInt("ingredient_id"))
                                .name(rs2.getString("name"))
                                .category(CategoryEnum.valueOf(rs2.getString("category")))
                                .price(rs2.getDouble("price"))
                                .build();

                        return DishIngredient.builder()
                                .ingredient(ingredient)
                                .quantity(1.0)
                                .build();
                    });

            return Dish.builder()
                    .id(dishId)
                    .name(name)
                    .price(price)
                    .dishIngredients(dishIngredients)
                    .build();
        });

        return dishes;
    }

    public Optional<Dish> findById(Integer id) {
        String sql = "SELECT id, name, selling_price, dish_type FROM dish WHERE id = ?";
        List<Dish> dishes = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) ->
                Dish.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .price(rs.getDouble("selling_price"))
                        .build()
        );
        return dishes.stream().findFirst();
    }

    public void removeAllIngredientsFromDish(Integer dishId) {
        String sql = "DELETE FROM dish_ingredient WHERE id_dish = ?";
        jdbcTemplate.update(sql, dishId);
    }

    public void addIngredientToDish(Integer dishId, Integer ingredientId) {
        String sql = "INSERT INTO dish_ingredient(id_dish, id_ingredient) VALUES (?, ?)";
        jdbcTemplate.update(sql, dishId, ingredientId);
    }

    public boolean ingredientExists(Integer ingredientId) {
        String sql = "SELECT COUNT(id) FROM ingredient WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{ingredientId}, Integer.class);
        return count != null && count > 0;
    }
}
