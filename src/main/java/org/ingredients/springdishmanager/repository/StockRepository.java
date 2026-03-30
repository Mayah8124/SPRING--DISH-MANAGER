package org.ingredients.springdishmanager.repository;

import org.ingredients.springdishmanager.model.*;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.Optional;

@Repository
public class StockRepository {

    private final DataSource dataSource;

    public StockRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Ingredient> findIngredientById(Integer id) {

        String sql = "SELECT id, name, price, category FROM ingredient WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = executeQuery(ps, id)) {

            if (rs.next()) {
                return Optional.of(
                        Ingredient.builder()
                                .id(rs.getInt("id"))
                                .name(rs.getString("name"))
                                .category(
                                        rs.getString("category") != null
                                                ? CategoryEnum.valueOf(rs.getString("category"))
                                                : null
                                )
                                .price(rs.getDouble("price"))
                                .build()
                );
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("DB error while fetching ingredient", e);
        }
    }

    public Optional<StockMovement> findStockMovementAt(Integer ingredientId, Instant at) {

        String sql = """
                SELECT id, type, creation_datetime, unit, quantity
                FROM stock_movement
                WHERE id_ingredient = ? AND creation_datetime <= ?
                ORDER BY creation_datetime DESC
                LIMIT 1
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ingredientId);
            ps.setTimestamp(2, Timestamp.from(at));

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(
                            StockMovement.builder()
                                    .id(rs.getInt("id"))
                                    .type(MovementTypeEnum.valueOf(rs.getString("type")))
                                    .creationDatetime(rs.getTimestamp("creation_datetime").toInstant())
                                    .value(StockValue.builder()
                                            .unit(Unit.valueOf(rs.getString("unit")))
                                            .quantity(rs.getDouble("quantity"))
                                            .build())
                                    .build()
                    );
                }

                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error while fetching stock", e);
        }
    }

    private ResultSet executeQuery(PreparedStatement ps, Integer id) throws SQLException {
        ps.setInt(1, id);
        return ps.executeQuery();
    }
}