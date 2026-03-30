package org.ingredients.springdishmanager.validator;

import org.ingredients.springdishmanager.model.Dish;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DishValidator {
    public void validateDish(List<Dish> dishes) {
        if (dishes == null ||  dishes.isEmpty()) {
            throw new RuntimeException("No dishes found");
        }
    }
}
