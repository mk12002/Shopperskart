package com.shopperskart.order_service.service;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.shopperskart.order_service.model.Order;
import com.shopperskart.order_service.dto.OrderRequest;
import com.shopperskart.order_service.model.OrderLineItems;

import lombok.RequiredArgsConstructor;

import com.shopperskart.order_service.dto.InventoryResponse;
import com.shopperskart.order_service.dto.OrderLineItemsDto;
import com.shopperskart.order_service.repository.OrderRepository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import jakarta.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Transactional

public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest) {
        // Logic to place the order
        // This could involve saving the order details to the database,
        // calculating totals, etc.
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems= orderRequest.getOrderLineItemsDtoList().stream()
        .map(this::mapToDto)
        .collect(Collectors.toCollection(ArrayList::new));
        
        
        order.setOrderLineItemsList(orderLineItems); // This should be replaced with actual mapping logic to convert DTOs to entity objects

        List<String> skuCodes = order.getOrderLineItemsList().stream()
        .map(OrderLineItems::getSkuCode).toList();

        //call inventory service
        InventoryResponse[] inventoryResponseArray=webClientBuilder.build().get().uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
            .retrieve()
            .bodyToMono(InventoryResponse[].class)
            .block(); // This is a blocking call, consider using reactive programming for better performance

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if(allProductsInStock) {
            orderRepository.save(order); // Save the order to the repository
        } else {
            throw new IllegalArgumentException("Product is not in stock");
        }
        //orderRepository.save(order); // Save the order to the repository
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        return orderLineItems;
    }
}
