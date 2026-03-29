package org.ingredients.springdishmanager.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class DishOrder {
    private Integer id;
    private Dish dish;
    private Integer quantity;
}
