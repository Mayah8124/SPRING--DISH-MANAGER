package org.ingredients.springdishmanager.controller;

import org.ingredients.springdishmanager.model.StockMovement;
import org.ingredients.springdishmanager.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
