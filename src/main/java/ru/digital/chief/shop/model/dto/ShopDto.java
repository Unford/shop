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
    private @Positive Long id;
    private @NotBlank String name;
    private @Pattern(regexp = "%d%d-%d%d") String workingHours;
    private @Pattern(regexp = "") String phone;
    private String category;
    private List<ProductDto> products;


}