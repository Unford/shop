package ru.digital.chief.shop.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link ru.digital.chief.shop.model.domain.Shop} entity
 */
public record ShopDto(@Positive Long id,
                      @NotBlank String name,
                      @Pattern(regexp = "%d%d-%d%d") String workingHours,
                      @Pattern(regexp = "") String phone,
                      String category,
                      List<ProductDto> products) implements Serializable {
}