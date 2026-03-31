package org.ingredients.springdishmanager.service;

import org.ingredients.springdishmanager.model.Ingredient;
import org.ingredients.springdishmanager.model.StockMovement;
import org.ingredients.springdishmanager.repository.StockRepository;
import org.ingredients.springdishmanager.validator.StockValidator;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    public StockMovement getStockBetween(Instant from, Instant at) {
        validator.validateDateParams(from, at);

        Optional<StockMovement>  stockMovement = repository.getStockMovementBetween(from, at);

        return stockMovement.orElse(null);
    }
}
