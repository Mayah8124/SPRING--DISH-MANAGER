package org.ingredients.springdishmanager.validator;

import org.ingredients.springdishmanager.model.Dish;
import org.ingredients.springdishmanager.model.Ingredient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DishValidator {
    public void validateDish(List<Dish> dishes) {
        if (dishes == null ||  dishes.isEmpty()) {
            throw new RuntimeException("No dishes found");
        }
    }

    public void validateDishFound(Optional<Dish> optional, Integer id) {
        if (optional.isEmpty()) {
            throw new RuntimeException("Dish.id=" + id + " is not found");
        }
    }

    public void validateIngredientsNotEmpty(List<Ingredient> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            throw new RuntimeException("Request body must contain at least one ingredient");
        }
    }
}
