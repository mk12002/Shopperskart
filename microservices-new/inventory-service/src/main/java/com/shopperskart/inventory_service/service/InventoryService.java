package com.shopperskart.inventory_service.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.shopperskart.inventory_service.repository.InventoryRepository;
import com.shopperskart.inventory_service.dto.InventoryResponse;
import com.shopperskart.inventory_service.model.Inventory;

//import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor


public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
            .map(inventory ->
                InventoryResponse.builder()
                    .skuCode(inventory.getSkuCode())
                    .isInStock(inventory.getQuantity() > 0)
                    .build()
            )
            .toList();

    }
}
