package ru.digital.chief.shop.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.digital.chief.shop.model.domain.Product;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link Product} entity
 */

@Data
public class ProductDto {

    private @Positive Long id;
    private @NotBlank String name;
    private @Positive BigDecimal price;
    private @Positive Long quantity;
    private @NotBlank String brand;
    private String category;
    private ShopDto shop;


}