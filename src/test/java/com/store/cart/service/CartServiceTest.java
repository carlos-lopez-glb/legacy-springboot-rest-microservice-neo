package com.store.cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.CartDto;

import com.store.cart.model.entity.CartEntity;
import com.store.cart.repository.CartRepository;
import com.store.cart.service.impl.CartServiceImpl;

/**
 * Unit tests for CartService using JUnit 5 and Mockito
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Cart Service Tests")
class CartServiceTest {

        @Mock
        private CartRepository cartRepository;

        @InjectMocks
        private CartServiceImpl cartService;

        private CartEntity cartEntity;
        private CartDto cartDto;

        @BeforeEach
        void setUp() {
                cartEntity = new CartEntity();
                cartEntity.setId(1L);
                cartEntity.setCustomerId(100L);
                cartEntity.setTotalPrice(250.0);

                cartDto = new CartDto();
                cartDto.setId(1L);
                // Note: CartDto properties will be determined by OpenAPI generation
        }

        @Test
        @DisplayName("Should find all carts successfully")
        void shouldFindAllCarts() {
                // Given
                List<CartEntity> cartEntities = Arrays.asList(cartEntity);
                when(cartRepository.findAll()).thenReturn(cartEntities);

                // When
                List<CartDto> result = cartService.findCarts();

                // Then
                assertThat(result).hasSize(1);
                assertThat(result.get(0).getId()).isEqualTo(1L);
                verify(cartRepository).findAll();
        }

        @Test
        @DisplayName("Should find cart by id successfully")
        void shouldFindCartById() {
                // Given
                when(cartRepository.findById(1L)).thenReturn(Optional.of(cartEntity));

                // When
                CartDto result = cartService.findCartById(1L);

                // Then
                assertThat(result).isNotNull();
                assertThat(result.getId()).isEqualTo(1L);
                verify(cartRepository).findById(1L);
        }

        @Test
        @DisplayName("Should throw exception when cart not found")
        void shouldThrowExceptionWhenCartNotFound() {
                // Given
                when(cartRepository.findById(anyLong())).thenReturn(Optional.empty());

                // When & Then
                assertThatThrownBy(() -> cartService.findCartById(999L))
                        .isInstanceOf(RuntimeException.class);
                verify(cartRepository).findById(999L);
        }

        @Test
        @DisplayName("Should create cart successfully")
        void shouldCreateCart() {
                // Given
                when(cartRepository.save(any(CartEntity.class))).thenReturn(cartEntity);

                // When
                CartDto result = cartService.createCart(cartDto);

                // Then
                assertThat(result).isNotNull();
                assertThat(result.getId()).isEqualTo(1L);
                verify(cartRepository).save(any(CartEntity.class));
        }

        @Test
        @DisplayName("Should update cart successfully")
        void shouldUpdateCart() {
                // Given
                CartDto updatedCartDto = new CartDto();
                updatedCartDto.setId(1L);

                when(cartRepository.save(any(CartEntity.class))).thenReturn(cartEntity);

                // When
                CartDto result = cartService.updateCartById(1L, updatedCartDto);

                // Then
                assertThat(result).isNotNull();
                assertThat(result.getId()).isEqualTo(1L);
                verify(cartRepository).save(any(CartEntity.class));
        }

        @Test
        @DisplayName("Should delete cart successfully")
        void shouldDeleteCart() {
                // Given
                doNothing().when(cartRepository).deleteById(1L);

                // When
                cartService.deleteCartById(1L);

                // Then
                verify(cartRepository).deleteById(1L);
        }

        @Test
        @DisplayName("Should check if cart exists")
        void shouldCheckIfCartExists() {
                // Given
                when(cartRepository.existsById(1L)).thenReturn(true);

                // When
                boolean exists = cartService.existsById(1L);

                // Then
                assertThat(exists).isTrue();
                verify(cartRepository).existsById(1L);
        }
}