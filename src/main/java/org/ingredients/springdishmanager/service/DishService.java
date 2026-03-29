package org.ingredients.springdishmanager.service;

import org.ingredients.springdishmanager.model.Dish;
import org.ingredients.springdishmanager.model.DishIngredient;
import org.springframework.stereotype.Service;


@Service
public class DishService {

    public Double calculateCost(Dish dish) {
        double totalPrice = 0;
        for (DishIngredient dishIngredient : dish.getDishIngredients()) {
            Double quantity = dishIngredient.getQuantity();
            if (quantity == null) {
                throw new RuntimeException("Some ingredients have undefined quantity");
            }
            totalPrice += dishIngredient.getIngredient().getPrice() * quantity;
        }
        return totalPrice;
    }
}
