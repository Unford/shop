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
import ru.digital.chief.shop.model.dto.ProductDto;
import ru.digital.chief.shop.service.ProductService;
import ru.digital.chief.shop.validation.UpdateValidation;

import java.util.List;

@RestController
@RequestMapping("/products")
@Validated

public class ProductController {
    private final ProductService service;


    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") @Positive long id) throws ServiceException {
        return service.findById(id);
    }

    @GetMapping
    public List<ProductDto> getProducts(
            @RequestParam(name = "page", required = false, defaultValue = "1") @Positive int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Positive int size)
            throws ServiceException {
        return service.findPage(page, size);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto) throws ServiceException {
        return service.create(productDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ProductDto> deleteProductById(@PathVariable("id") @Positive long id)
            throws ServiceException {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ProductDto updateProductById(@PathVariable("id") @Positive long id,
                             @RequestBody @Validated({UpdateValidation.class, Default.class})
                             ProductDto productDto)
            throws ServiceException {
        productDto.setId(id);
        return service.update(productDto);
    }


}
