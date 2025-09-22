package com.store.cart.contract;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import com.store.cart.controller.CartController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

/**
 * Base class for Spring Cloud Contract tests using JUnit 5
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@SpringJUnitConfig
@ActiveProfiles("test")
@DirtiesContext
@AutoConfigureMessageVerifier
public abstract class ContractTestBase {

        @Autowired
        public CartController cartController;

        @BeforeEach
        public void setup() {
                StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(cartController);
                RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
        }

}