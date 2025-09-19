package com.store.cart.repository;

import com.store.cart.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  */
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}