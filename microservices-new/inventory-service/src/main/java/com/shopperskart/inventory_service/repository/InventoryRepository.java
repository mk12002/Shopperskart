package com.shopperskart.inventory_service.repository;

import com.shopperskart.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    //boolean existsBySkuCode(String skuCode);
   // Optional<Inventory> findBySkuCode(String skuCode);
    List<Inventory> findBySkuCodeIn(List<String> skuCode);

}
