package com.shopperskart.inventory_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import com.shopperskart.inventory_service.service.InventoryService;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.shopperskart.inventory_service.dto.InventoryResponse;


@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor

public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        // This is a placeholder implementation.
        // In a real application, you would call a service to check the inventory.
        return inventoryService.isInStock(skuCode);
    }
}
