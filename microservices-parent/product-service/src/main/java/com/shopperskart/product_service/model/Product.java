 package com.shopperskart.product_service.model;

 import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;


import java.math.BigDecimal;

@Document(value = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    // private String category;
    // private String brand;
    // private int stockQuantity;

    @ToString.Exclude
    private String imageUrl; // Exclude from toString to avoid large data exposure

}
