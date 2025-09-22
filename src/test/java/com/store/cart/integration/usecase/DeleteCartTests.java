package com.store.cart.integration.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.openapitools.model.CartDto;
import org.openapitools.model.ErrorResponse;
import com.store.cart.integration.IntegrationTestBase;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class DeleteCartTests extends IntegrationTestBase {

	// <-- Positive DELETE Request Integration Tests -->
	@Test
	void GIVEN_existingCartId_WHEN_deleteRequestToCartById_THEN_noContent() {
		// given
		// First create a cart to delete
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
		ResponseEntity<Void> deleteResponse = restTemplate.exchange(
			getBaseUrl() + CART_API_BASE_PATH + "/" + existingCartId,
			HttpMethod.DELETE,
			null,
			Void.class
		);

		// then
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	// <-- Negative DELETE Request Integration Tests -->
	@Test
	void GIVEN_nonExistingCartId_WHEN_deleteRequestToCartById_THEN_notFound() {
		// given
		long nonExistingCartId = 999L;

		// when
		ResponseEntity<ErrorResponse> response = restTemplate.exchange(
			getBaseUrl() + CART_API_BASE_PATH + "/" + nonExistingCartId,
			HttpMethod.DELETE,
			null,
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