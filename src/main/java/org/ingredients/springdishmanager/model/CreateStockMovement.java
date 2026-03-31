package org.ingredients.springdishmanager.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder

public class CreateStockMovement {
    private MovementTypeEnum type;
    private Double quantity;
    private Unit unit;
}
