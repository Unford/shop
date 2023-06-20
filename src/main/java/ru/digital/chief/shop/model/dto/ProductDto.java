package ru.digital.chief.shop.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link ru.digital.chief.shop.model.domain.Product} entity
 */
@Data
public class ProductDto implements Serializable {
    private final Long id;
    private final String name;
    private final BigDecimal price;
    private final Long quantity;
    private final String brand;
    private final String category;
}