package ru.digital.chief.shop.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.digital.chief.shop.model.domain.Shop;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link Shop} entity
 */
@Data
public class ShopDto {
    private final @Positive Long id;
    private final @NotBlank String name;
    private final @Pattern(regexp = "%d%d-%d%d") String workingHours;
    private final @Pattern(regexp = "") String phone;
    private final String category;
    private final List<ProductDto> products;


}