package org.ingredients.springdishmanager.service;

import org.ingredients.springdishmanager.model.Dish;
import org.ingredients.springdishmanager.model.DishIngredient;
import org.ingredients.springdishmanager.repository.DishRepository;
import org.ingredients.springdishmanager.validator.DishValidator;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DishService {

    private final DishRepository repository;
    private final DishValidator validator;

    public DishService(DishRepository repository, DishValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

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

    public List<Dish> getAllDishes() {
        List<Dish> dishes = repository.findAll();
        validator.validateDish(dishes);
        return dishes;
    }
}
