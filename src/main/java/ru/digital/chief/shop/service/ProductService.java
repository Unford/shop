package ru.digital.chief.shop.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.digital.chief.shop.exception.CustomErrorCode;
import ru.digital.chief.shop.exception.ServiceException;
import ru.digital.chief.shop.model.domain.Product;
import ru.digital.chief.shop.model.dto.ProductDto;
import ru.digital.chief.shop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public void deleteById(long id) throws ServiceException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ServiceException(Long.toString(id),
                        CustomErrorCode.RESOURCE_NOT_FOUND));
        productRepository.delete(product);
    }

    public ProductDto findById(long id) throws ServiceException {
        Optional<Product> result = productRepository.findById(id);
        Product tag = result.orElseThrow(() -> new ServiceException(Long.toString(id),
                CustomErrorCode.RESOURCE_NOT_FOUND));
        return modelMapper.map(tag, ProductDto.class);
    }

    public List<ProductDto> findPage(int page, int size) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size));
        return productPage.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDto.class)).toList();

    }

    public ProductDto create(ProductDto productDto) throws ServiceException {
        if (productRepository.findProductByName(productDto.name()).isPresent()) {
            throw new ServiceException(productDto.name(), CustomErrorCode.RESOURCE_ALREADY_EXIST);
        }
        Product newProduct = productRepository.save(modelMapper.map(productDto, Product.class));
        modelMapper.map(newProduct, productDto);
        return productDto;
    }
}
