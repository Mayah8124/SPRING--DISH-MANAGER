package org.ingredients.springdishmanager.controller;


import org.ingredients.springdishmanager.model.Ingredient;
import org.ingredients.springdishmanager.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ingredient> getAllIngredients() {
        return service.getAllIngredients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Ingredient ingredient = service.getIngredientById(id);
            return ResponseEntity.ok(ingredient);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404)
                    .body(e.getMessage());
        }
    }
}
