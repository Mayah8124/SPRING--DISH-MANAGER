package org.ingredients.springdishmanager.validator;

import org.ingredients.springdishmanager.model.Ingredient;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class IngredientValidator {
    public Ingredient validateFoundIngredient(Optional<Ingredient> ingredient, Integer id) {
        return ingredient.orElseThrow(() -> new RuntimeException("Ingredient not found"));

    }
}
