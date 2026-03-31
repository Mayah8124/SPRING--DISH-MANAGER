package org.ingredients.springdishmanager.validator;

import org.ingredients.springdishmanager.model.CreateStockMovement;
import org.ingredients.springdishmanager.model.Ingredient;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
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

    public void validateDateParams(Instant from, Instant to) {
        if (from == null || to == null) {
            throw new RuntimeException("Each date parameter `from` and `to` are required");
        }

        if (from.isAfter(to)) {
            throw new RuntimeException("`from` must be before `to`");
        }
    }

    public void validateCreateInputs(List<CreateStockMovement> inputs) {

        if (inputs == null || inputs.isEmpty()) {
            throw new RuntimeException("Stock movements list cannot be empty");
        }

        for (CreateStockMovement input : inputs) {

            if (input.getType() == null) {
                throw new RuntimeException("Movement type is required");
            }

            if (input.getUnit() == null) {
                throw new RuntimeException("Unit is required");
            }

            if (input.getQuantity() == null) {
                throw new RuntimeException("Quantity is required");
            }

            if (input.getQuantity() <= 0) {
                throw new RuntimeException("Quantity must be greater than 0");
            }
        }
    }
}
