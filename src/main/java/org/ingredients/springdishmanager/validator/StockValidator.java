package org.ingredients.springdishmanager.validator;

import org.ingredients.springdishmanager.model.Ingredient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StockValidator {
    public void validateIngredientFound(Optional<Ingredient> optional, Integer id) {
        if (optional.isEmpty()) {
            throw new RuntimeException("Ingredient.id=" + id + " is not found");
        }
    }

    public void validateQueryParameters(String at, String unit) {
        if (at == null || at.isBlank() || unit == null || unit.isBlank()) {
            throw new RuntimeException("Either mandatory query parameter `at` or `unit` is not provided");
        }
    }
}
