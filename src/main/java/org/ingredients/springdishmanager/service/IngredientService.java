package org.ingredients.springdishmanager.service;


import org.ingredients.springdishmanager.model.Ingredient;
import org.ingredients.springdishmanager.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public List<Ingredient> getAllIngredients() {
        return repository.findAll();
    }
}
