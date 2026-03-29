package org.ingredients.springdishmanager.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class DishIngredient {
    private Dish dish;
    private Ingredient ingredient;
    private Double quantity;
    private Unit unit;
}
