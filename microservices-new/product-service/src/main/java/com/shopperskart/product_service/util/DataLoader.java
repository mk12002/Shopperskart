package com.shopperskart.product_service.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.shopperskart.product_service.model.Product;
import com.shopperskart.product_service.repository.ProductRepository;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() < 1) {
            Product product = new Product();
            product.setName("iPhone 13");
            product.setDescription("iPhone 13");
            product.setPrice(BigDecimal.valueOf(1000));

            productRepository.save(product);
        }
    }
}
