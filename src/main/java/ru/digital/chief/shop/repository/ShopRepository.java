package ru.digital.chief.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.digital.chief.shop.model.domain.Shop;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findShopByName(String name);
}
