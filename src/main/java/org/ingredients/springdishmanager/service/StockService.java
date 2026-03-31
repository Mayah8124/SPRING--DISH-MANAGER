package org.ingredients.springdishmanager.service;

import org.ingredients.springdishmanager.model.CreateStockMovement;
import org.ingredients.springdishmanager.model.Ingredient;
import org.ingredients.springdishmanager.model.StockMovement;
import org.ingredients.springdishmanager.repository.StockRepository;
import org.ingredients.springdishmanager.validator.StockValidator;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    private final StockRepository repository;
    private final StockValidator validator;

    public StockService(StockRepository repository, StockValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public StockMovement getStock(Integer ingredientId, String at, String unitStr) {
        validator.validateQueryParameters(at, unitStr);

        Optional<Ingredient> optionalIngredient = repository.findIngredientById(ingredientId);
        validator.validateIngredientFound(optionalIngredient, ingredientId);

        Instant timestamp = Instant.parse(at);
        Optional<StockMovement> stockMovement = repository.findStockMovementAt(ingredientId, timestamp);

        return stockMovement.orElse(null);
    }

    public List<StockMovement> getStockBetween(Integer id, Instant from, Instant to) {

        validator.validateDateParams(from, to);

        if (!repository.ingredientExists(id)) {
            throw new RuntimeException("Ingredient.id=" + id + " is not found");
        }

        return repository.getStockMovementsBetween(id, from, to);
    }

    public List<StockMovement> create(Integer ingredientId, List<CreateStockMovement> inputs) {

        validator.validateCreateInputs(inputs);

        if (!repository.ingredientExists(ingredientId)) {
            throw new RuntimeException("Ingredient.id=" + ingredientId + " is not found");
        }

        return repository.saveAll(ingredientId, inputs);
    }
}
