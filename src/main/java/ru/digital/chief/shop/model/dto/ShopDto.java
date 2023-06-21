package ru.digital.chief.shop.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import ru.digital.chief.shop.model.domain.Shop;
import ru.digital.chief.shop.validation.NullOrNotBlank;
import ru.digital.chief.shop.validation.ProductCreateValidation;
import ru.digital.chief.shop.validation.ShopCreateValidation;

import java.util.List;

/**
 * A DTO for the {@link Shop} entity
 */
@Data
public class ShopDto {
    private @Positive @NotNull(groups = ProductCreateValidation.class) Long id;
    private @NotBlank(groups = {ShopCreateValidation.class})
    @Size(min = 3, max = 255) String name;
    private @Pattern(regexp = "\\d\\d-\\d\\d") @NotNull(groups = {ShopCreateValidation.class}) String workingHours;
    private @NotNull(groups = {ShopCreateValidation.class})
    @Pattern(regexp = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$")
    String phone;
    private @NullOrNotBlank
    @Size(min = 3, max = 255) String category;
    private @NullOrNotBlank
    @Size(min = 3, max = 255) String email;
    private List<@Valid ProductDto> products;


}