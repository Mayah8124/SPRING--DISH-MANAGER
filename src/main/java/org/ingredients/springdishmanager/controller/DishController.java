package org.ingredients.springdishmanager.controller;

import org.ingredients.springdishmanager.model.Dish;
import org.ingredients.springdishmanager.model.Ingredient;
import org.ingredients.springdishmanager.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {
    private final DishService service;

    public DishController(DishService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getDishes() {
        try {
            List<Dish> dishes = service.getAllDishes();
            return new ResponseEntity<>(dishes, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> updateDishIngredients(@PathVariable Integer id,
                                                   @RequestBody(required = false) List<Ingredient> ingredients) {
        try {
            service.updateDishIngredients(id, ingredients);
            return ResponseEntity.ok("Ingredients updated successfully for Dish.id=" + id);
        } catch (RuntimeException e) {
            if ("Request body must contain at least one ingredient".equals(e.getMessage())) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } else if (e.getMessage().startsWith("Dish.id=")) {
                return ResponseEntity.status(404).body(e.getMessage());
            } else {
                return ResponseEntity.status(500).body("Unexpected error: " + e.getMessage());
            }
        }
    }
}
