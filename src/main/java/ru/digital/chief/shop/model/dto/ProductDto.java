package ru.digital.chief.shop.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link ru.digital.chief.shop.model.domain.Product} entity
 */

public record ProductDto(@Positive Long id,
                         @NotBlank String name,
                         @Positive BigDecimal price,
                         @Positive Long quantity,
                         @NotBlank String brand,
                         String category) implements Serializable {
}