package com.store.cart.repository;

import com.store.cart.model.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  */
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}