package ru.digital.chief.shop.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.digital.chief.shop.exception.ServiceException;
import ru.digital.chief.shop.model.dto.ShopDto;
import ru.digital.chief.shop.service.impl.ShopService;
import ru.digital.chief.shop.validation.UpdateValidation;

import java.util.List;

@RestController
@RequestMapping("/shops")
@Validated
public class ShopController {
    private final ShopService service;

    @Autowired
    public ShopController(ShopService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ShopDto getShopById(@PathVariable("id") @Positive long id) throws ServiceException {
        return service.findById(id);
    }

    @GetMapping
    public List<ShopDto> getShops(
            @RequestParam(name = "page", required = false, defaultValue = "1") @Positive int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Positive int size) {
        return service.findPage(page, size);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShopDto createShop(@RequestBody @Valid ShopDto shopDto) throws ServiceException {
        return service.create(shopDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ShopDto> deleteShopById(@PathVariable("id") @Positive long id)
            throws ServiceException {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ShopDto updateShopById(@PathVariable("id") @Positive long id,
                                        @RequestBody @Validated({UpdateValidation.class, Default.class})
                                        ShopDto shopDto)
            throws ServiceException {
        shopDto.setId(id);
        return service.update(shopDto);
    }
}
