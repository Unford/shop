package ru.digital.chief.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.digital.chief.shop.model.domain.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
