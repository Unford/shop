package ru.digital.chief.shop.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link ru.digital.chief.shop.model.domain.Shop} entity
 */
@Data
public class ShopDto implements Serializable {
    private final Long id;
    private final String name;
    private final String workingHours;
    private final String phone;
    private final String category;
    private final List<ProductDto> products;
}