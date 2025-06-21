package com.shopperskart.inventory_service.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.shopperskart.inventory_service.repository.InventoryRepository;

//import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor


public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();

    }
}
