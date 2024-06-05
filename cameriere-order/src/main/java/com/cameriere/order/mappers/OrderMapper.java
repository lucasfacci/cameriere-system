package com.cameriere.order.mappers;

import com.cameriere.order.dtos.OrderRequestDTO;
import com.cameriere.order.dtos.OrderResponseDTO;
import com.cameriere.order.models.Order;
import com.cameriere.order.proxies.ProductProxy;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderMapper {

    private ProductProxy productProxy;

    public static OrderResponseDTO mapToOrderResponseDTOFromOrder(Order order, OrderResponseDTO orderResponseDTO) {
        orderResponseDTO.setTableNumber(order.getTableNumber());
        orderResponseDTO.setTotalPrice(order.getTotalPrice());
        return orderResponseDTO;
    }

    public static Order mapToOrderFromOrderRequestDTO(OrderRequestDTO orderRequestDTO, Order order) {
        order.setTableNumber(orderRequestDTO.getTableNumber());
        order.setProducts(orderRequestDTO.getProducts());
        return order;
    }
}
