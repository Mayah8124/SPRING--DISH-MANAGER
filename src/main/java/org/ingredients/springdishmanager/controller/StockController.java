package org.ingredients.springdishmanager.controller;

import org.ingredients.springdishmanager.model.CreateStockMovement;
import org.ingredients.springdishmanager.model.StockMovement;
import org.ingredients.springdishmanager.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class StockController {
    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<?> getStock(@PathVariable Integer id,
                                      @RequestParam(required = false) String at,
                                      @RequestParam(required = false) String unit) {
        try {
            StockMovement stockMovement = service.getStock(id, at, unit);
            return ResponseEntity.ok(stockMovement);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not provided")) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } else if (e.getMessage().contains("ingredient not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            } else {
                return ResponseEntity.status(500).body(e.getMessage());
            }
        }
    }

    @GetMapping("/{id}/stockMovements")
    public ResponseEntity<?> getStockMovements(
            @PathVariable Integer id,
            @RequestParam Instant from,
            @RequestParam Instant to
    ) {
        try {
            List<StockMovement> stockMovements = service.getStockBetween(id, from, to);
            return ResponseEntity.ok(stockMovements);

        } catch (RuntimeException e) {

            if (e.getMessage().contains("required")) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }

            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/stockMovements")
    public ResponseEntity<?> createStockMovements(
            @PathVariable Integer id,
            @RequestBody List<CreateStockMovement> body
    ) {

        try {
            List<StockMovement> result = service.create(id, body);
            return ResponseEntity.ok(result);

        } catch (RuntimeException e) {

            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
