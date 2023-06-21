package ru.digital.chief.shop.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    private String workingHours;
    private String phone;
    private String email;

    private String category;

    @OneToMany(mappedBy = "shop", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    private List<Product> products = new java.util.ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Shop shop = (Shop) o;
        return id != null && Objects.equals(id, shop.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
