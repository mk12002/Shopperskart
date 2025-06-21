package com.shopperskart.order_service.service;

import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shopperskart.order_service.model.Order;
import com.shopperskart.order_service.dto.OrderRequest;
import com.shopperskart.order_service.model.OrderLineItems;

import lombok.RequiredArgsConstructor;

import com.shopperskart.order_service.dto.OrderLineItemsDto;
import com.shopperskart.order_service.repository.OrderRepository;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        // Logic to place the order
        // This could involve saving the order details to the database,
        // calculating totals, etc.
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems= orderRequest.getOrderLineItemsDtoList().stream()
        .map(this::mapToDto)
        .toList();
        
        order.setOrderLineItemsList(orderLineItems); // This should be replaced with actual mapping logic to convert DTOs to entity objects

        orderRepository.save(order); // Save the order to the repository
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        return orderLineItems;
    }
}
