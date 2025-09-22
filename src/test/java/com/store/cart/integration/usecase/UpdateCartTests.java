package com.store.cart.integration.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.openapitools.model.CartDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.store.cart.integration.IntegrationTestBase;

class UpdateCartTests extends IntegrationTestBase {

        @Test
        void GIVEN_updatedCartWithExistingId_WHEN_putRequestToCartById_THEN_ok() {
                // given
                long existingCartId = 1L;

                // First create a cart to update
                CartDto newCart = new CartDto();
                newCart.setCustomerId(100L);
                newCart.setTotalPrice(100.0);

                ResponseEntity<CartDto> createResponse = restTemplate.postForEntity(
                        getBaseUrl() + CART_API_BASE_PATH,
                        newCart,
                        CartDto.class
                );

                assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                CartDto createdCart = createResponse.getBody();
                assertThat(createdCart).isNotNull();

                // Update the cart
                createdCart.setTotalPrice(111.11);

                // when
                HttpEntity<CartDto> requestEntity = new HttpEntity<>(createdCart);
                ResponseEntity<CartDto> updateResponse = restTemplate.exchange(
                        getBaseUrl() + CART_API_BASE_PATH + "/" + createdCart.getId(),
                        HttpMethod.PUT,
                        requestEntity,
                        CartDto.class
                );

                // then
                assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(updateResponse.getBody()).isNotNull();
                assertThat(updateResponse.getBody().getTotalPrice()).isEqualTo(111.11);
        }

        @Test
        void GIVEN_updatedCardWithNonExistingId_WHEN_putRequestToCartById_THEN_created() {
                // given
                long nonExistingCartId = 999L;

                CartDto cart = new CartDto();
                cart.setId(nonExistingCartId);
                cart.setCustomerId(200L);
                cart.setTotalPrice(222.22);

                // when
                HttpEntity<CartDto> requestEntity = new HttpEntity<>(cart);
                ResponseEntity<CartDto> response = restTemplate.exchange(
                        getBaseUrl() + CART_API_BASE_PATH + "/" + nonExistingCartId,
                        HttpMethod.PUT,
                        requestEntity,
                        CartDto.class
                );

                // then
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                assertThat(response.getBody()).isNotNull();
                assertThat(response.getBody().getTotalPrice()).isEqualTo(222.22);
        }

}