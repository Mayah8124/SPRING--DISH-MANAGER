package org.ingredients.springdishmanager.controller;


import org.ingredients.springdishmanager.model.Ingredient;
import org.ingredients.springdishmanager.service.IngredientService;
import org.springframework.web.bind.annotation.GetMapping;
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
}
