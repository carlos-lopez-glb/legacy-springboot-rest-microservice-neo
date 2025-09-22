package com.store.cart.integration.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.openapitools.model.CartDto;
import org.openapitools.model.ErrorResponse;
import com.store.cart.integration.IntegrationTestBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ReadCartTests extends IntegrationTestBase {

        // <-- Positive GET Requests Tests -->
        @Test
        void WHEN_getRequestToCarts_THEN_ok() {
                // when
                ResponseEntity<List> response = restTemplate.getForEntity(
                        getBaseUrl() + CART_API_BASE_PATH,
                        List.class
                );

                // then
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotNull();
        }

        @Test
        void GIVEN_existingCartId_WHEN_getRequestToCartById_THEN_ok() {
                // given
                // First create a cart
                CartDto newCart = new CartDto();
                newCart.setCustomerId(100L);
                newCart.setTotalPrice(100.0);

                ResponseEntity<CartDto> createResponse = restTemplate.postForEntity(
                        getBaseUrl() + CART_API_BASE_PATH,
                        newCart,
                        CartDto.class
                );

                assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                Long existingCartId = createResponse.getBody().getId();

                // when
                ResponseEntity<CartDto> response = restTemplate.getForEntity(
                        getBaseUrl() + CART_API_BASE_PATH + "/" + existingCartId,
                        CartDto.class
                );

                // then
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotNull();
                assertThat(response.getBody().getId()).isEqualTo(existingCartId);
        }

        // <-- Negative GET Requests Tests -->
        @Test
        void GIVEN_nonExistingCartId_WHEN_getRequestToCartById_THEN_notFound() {
                // given
                long nonExistingCartId = 999L;

                // when
                ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(
                        getBaseUrl() + CART_API_BASE_PATH + "/" + nonExistingCartId,
                        ErrorResponse.class
                );

                // then
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(response.getBody()).isNotNull();
                assertThat(response.getBody().getErrorCode()).isEqualTo(404);
                assertThat(response.getBody().getDeveloperMessage()).isEqualTo("Record not found.");
                assertThat(response.getBody().getMessage()).contains("Cart with id:{[" + nonExistingCartId + "}] does not exist.");
        }

}

