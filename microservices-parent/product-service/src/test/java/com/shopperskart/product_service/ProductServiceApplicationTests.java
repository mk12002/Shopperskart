package com.shopperskart.product_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.math.BigDecimal;
import com.shopperskart.product_service.dto.ProductRequest;
import com.shopperskart.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc 
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
		//registry.add("spring.data.mongodb.database", () -> "product_service_db");
	}

	@Test
	public void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest(); 
		String productRequestString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
		.contentType(MediaType.APPLICATION_JSON)
		.content(productRequestString))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		Assertions.assertTrue(productRepository.findAll().size()==1);
	}
	private ProductRequest getProductRequest(){
		return ProductRequest.builder()
			.name("Test Product")
			.price(BigDecimal.valueOf(100.0))
			.description("Test Description")
			.build();
	}

}
