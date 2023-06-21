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
import ru.digital.chief.shop.repository.ProductRepository;
import ru.digital.chief.shop.repository.ShopRepository;
import ru.digital.chief.shop.service.BasicService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService implements BasicService<ProductDto> {
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;

    @Override
    public void deleteById(long id) throws ServiceException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ServiceException(Long.toString(id),
                        CustomErrorCode.RESOURCE_NOT_FOUND));
        productRepository.delete(product);
    }

    @Override
    public ProductDto findById(long id) throws ServiceException {
        Optional<Product> result = productRepository.findById(id);
        Product product = result.orElseThrow(() -> new ServiceException(Long.toString(id),
                CustomErrorCode.RESOURCE_NOT_FOUND));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public List<ProductDto> findPage(int page, int size) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size));
        return productPage.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDto.class)).toList();

    }

    @Override
    public ProductDto create(ProductDto productDto) {
        Product newProduct = productRepository.save(modelMapper.map(productDto, Product.class));
        modelMapper.map(newProduct, productDto);
        return productDto;
    }

    @Override
    public ProductDto update(ProductDto productDto) throws ServiceException {
        Product product = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new ServiceException(Long.toString(productDto.getId()),
                        CustomErrorCode.RESOURCE_NOT_FOUND));
        setAllNotNullFields(productDto, product);
        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    private void setAllNotNullFields(ProductDto source, Product destination) throws ServiceException {
        if (source.getShop() != null && source.getShop().getId() != null) {
            Long shopId = source.getShop().getId();
            Shop shop = shopRepository.findById(shopId)
                    .orElseThrow(() -> new ServiceException(Long.toString(shopId),
                            CustomErrorCode.RESOURCE_NOT_FOUND));
            destination.setShop(shop);
        }
        if (source.getName() != null) {
            destination.setName(source.getName());
        }
        if (source.getBrand() != null) {
            destination.setBrand(source.getBrand());
        }
        if (source.getCategory() != null) {
            destination.setCategory(source.getCategory());
        }
        if (source.getPrice() != null) {
            destination.setPrice(source.getPrice());
        }
        if (source.getQuantity() != null) {
            destination.setQuantity(source.getQuantity());
        }
    }
}
