package ru.digital.chief.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import ru.digital.chief.shop.model.domain.Product;
import ru.digital.chief.shop.validation.NullOrNotBlank;
import ru.digital.chief.shop.validation.ProductCreateValidation;
import ru.digital.chief.shop.validation.ShopCreateValidation;
import ru.digital.chief.shop.validation.ShopUpdateValidation;

import java.math.BigDecimal;

/**
 * A DTO for the {@link Product} entity
 */

@Data
@Builder
public class ProductDto {

    private @Positive @NotNull(groups = {ShopUpdateValidation.class}) Long id;
    private @NotBlank(groups = {ProductCreateValidation.class,
            ShopCreateValidation.class}) @Size(min = 3, max = 255) String name;
    private @Positive @NotNull(groups = {ProductCreateValidation.class}) BigDecimal price;
    private @Positive @NotNull(groups = {ProductCreateValidation.class}) Long quantity;
    private @NotBlank(groups = {ProductCreateValidation.class}) @Size(min = 3, max = 255) String brand;
    private @NullOrNotBlank @Size(min = 3, max = 255) String category;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private @NotNull(groups = ProductCreateValidation.class) @Valid ShopDto shop;


}