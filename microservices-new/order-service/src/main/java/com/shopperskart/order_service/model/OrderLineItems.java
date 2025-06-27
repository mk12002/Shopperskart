package com.shopperskart.order_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "t_order_line_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderLineItems {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private Integer quantity;
    private BigDecimal price;
}
