package com.online.store.server.repositories;

import com.online.store.server.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Boolean existsByName(String name);
}
