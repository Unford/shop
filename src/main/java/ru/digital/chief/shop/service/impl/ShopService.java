package ru.digital.chief.shop.service.impl;


import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.digital.chief.shop.exception.CustomErrorCode;
import ru.digital.chief.shop.exception.ServiceException;
import ru.digital.chief.shop.model.domain.Product;
import ru.digital.chief.shop.model.domain.Shop;
import ru.digital.chief.shop.model.dto.ProductDto;
import ru.digital.chief.shop.model.dto.ShopDto;
import ru.digital.chief.shop.repository.ProductRepository;
import ru.digital.chief.shop.repository.ShopRepository;
import ru.digital.chief.shop.service.BasicService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShopService implements BasicService<ShopDto> {
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Override
    public void deleteById(long id) throws ServiceException {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ServiceException(Long.toString(id),
                        CustomErrorCode.RESOURCE_NOT_FOUND));
        shopRepository.delete(shop);
    }

    @Override
    public ShopDto findById(long id) throws ServiceException {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ServiceException(Long.toString(id),
                        CustomErrorCode.RESOURCE_NOT_FOUND));
        return modelMapper.map(shop, ShopDto.class);
    }

    @Override
    public List<ShopDto> findPage(int page, int size) {
        Page<Shop> shopPage = shopRepository.findAll(PageRequest.of(page, size));
        return shopPage.getContent().stream()
                .map(shop -> modelMapper.map(shop, ShopDto.class)).toList();
    }

    @Override
    public ShopDto create(ShopDto dto) throws ServiceException {
        if (shopRepository.findShopByName(dto.getName()).isPresent()) {
            throw new ServiceException(dto.getName(), CustomErrorCode.RESOURCE_ALREADY_EXIST);
        }
        Shop newShop = shopRepository.save(modelMapper.map(dto, Shop.class));
        modelMapper.map(newShop, dto);
        return dto;
    }

    @Override
    public ShopDto update(ShopDto dto) throws ServiceException {
        Shop shop = shopRepository.findById(dto.getId())
                .orElseThrow(() -> new ServiceException(Long.toString(dto.getId()),
                        CustomErrorCode.RESOURCE_NOT_FOUND));
        setAllNotNullFields(dto, shop);
        Shop updatedShop = shopRepository.save(shop);
        return modelMapper.map(updatedShop, ShopDto.class);
    }

    private void setAllNotNullFields(ShopDto source, Shop destination) throws ServiceException {
        if (source.getProducts() != null) {
            List<ProductDto> sourceProducts = source.getProducts();
            List<Product> products = new ArrayList<>();
            for (ProductDto dto : sourceProducts) {
                Optional<Product> product = productRepository.findById(dto.getId());
                product.ifPresent(products::add);
            }

            destination.setProducts(products);
        }
        if (source.getName() != null) {
            if (shopRepository.findShopByName(source.getName()).isPresent()) {
                throw new ServiceException(source.getName(), CustomErrorCode.RESOURCE_ALREADY_EXIST);
            }
            destination.setName(source.getName());
        }
        if (source.getPhone() != null) {
            destination.setPhone(source.getPhone());
        }
        if (source.getCategory() != null) {
            destination.setCategory(source.getCategory());
        }
        if (source.getWorkingHours() != null) {
            destination.setWorkingHours(source.getWorkingHours());
        }

    }
}
