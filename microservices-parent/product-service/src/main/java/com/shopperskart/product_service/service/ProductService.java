package com.shopperskart.product_service.service;
import com.shopperskart.product_service.dto.ProductRequest;
import com.shopperskart.product_service.model.Product;
import org.springframework.stereotype.Service;
import com.shopperskart.product_service.repository.ProductRepository;
import com.shopperskart.product_service.dto.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

private final ProductRepository productRepository;


    public void createProduct(ProductRequest productRequest) {
        // Logic to convert ProductRequest to Product and save it using ProductRepository
        // This method will handle the business logic for creating a product
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product created successfully: {}", product.getName());
    }

    public List<ProductResponse> getAllProducts() {
        // Logic to retrieve all products from the repository
        // This method will handle the business logic for retrieving all products
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build())
                .collect(Collectors.toList());
       
    }

}
