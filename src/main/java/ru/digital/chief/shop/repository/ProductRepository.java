package ru.digital.chief.shop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.digital.chief.shop.model.domain.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
