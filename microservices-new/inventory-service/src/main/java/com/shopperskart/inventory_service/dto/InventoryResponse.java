package com.shopperskart.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;

    // You can add other fields if necessary, such as quantity, warehouse location, etc.
}
