package org.ingredients.springdishmanager.service;


import org.ingredients.springdishmanager.model.Ingredient;
import org.ingredients.springdishmanager.repository.IngredientRepository;
import org.ingredients.springdishmanager.validator.IngredientValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    private final IngredientRepository repository;
    private final IngredientValidator validator;

    public IngredientService(IngredientRepository repository, IngredientValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<Ingredient> getAllIngredients() {
        return repository.findAll();
    }

    public Ingredient getIngredientById(Integer id) {
        Optional<Ingredient> ingredient = repository.findById(id);
        return validator.validateFoundIngredient(ingredient, id);
    }
}
