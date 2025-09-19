package com.store.cart.repository;

import com.store.cart.model.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  */
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {
}