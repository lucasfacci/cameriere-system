package com.cameriere.order.mappers;

import com.cameriere.order.dtos.OrderRequestDTO;
import com.cameriere.order.dtos.OrderResponseDTO;
import com.cameriere.order.models.Order;
import com.cameriere.order.services.clients.ProductFeignClient;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderMapper {

    public static OrderResponseDTO mapToOrderResponseDTOFromOrder(Order order, OrderResponseDTO orderResponseDTO) {
        orderResponseDTO.setTableNumber(order.getTableNumber());
        orderResponseDTO.setTotalPrice(order.getTotalPrice());
        orderResponseDTO.setStatus(order.getStatus());
        orderResponseDTO.setNote(order.getNote());
        orderResponseDTO.setPendingTimestamp(order.getPendingTimestamp());
        orderResponseDTO.setConfirmedTimestamp(order.getConfirmedTimestamp());
        orderResponseDTO.setPreparingTimestamp(order.getPreparingTimestamp());
        orderResponseDTO.setReadyTimestamp(order.getReadyTimestamp());
        orderResponseDTO.setCompletedTimestamp(order.getCompletedTimestamp());
        orderResponseDTO.setCancelledTimestamp(order.getCancelledTimestamp());
        return orderResponseDTO;
    }

    public static Order mapToOrderFromOrderRequestDTO(OrderRequestDTO orderRequestDTO, Order order) {
        order.setTableNumber(orderRequestDTO.getTableNumber());
        order.setProducts(orderRequestDTO.getProducts());
        order.setNote(orderRequestDTO.getNote());
        return order;
    }
}
