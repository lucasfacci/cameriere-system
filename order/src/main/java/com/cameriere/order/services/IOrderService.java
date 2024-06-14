package com.cameriere.order.services;

import com.cameriere.order.dtos.OrderRequestDTO;
import com.cameriere.order.dtos.OrderResponseDTO;

import java.util.List;

public interface IOrderService {

    /**
     *
     * @param correlationId - UUID to trace the request
     * @return All the orders
     */
    List<OrderResponseDTO> listOrders(String correlationId);

    /**
     *
     * @param id - Input ID
     * @param correlationId - UUID to trace the request
     * @return An order based on a given ID
     */
    OrderResponseDTO getOrder(Long id, String correlationId);

    /**
     *
     * @param orderRequestDTO - OrderRequestDTO Object
     * @param correlationId - UUID to trace the request
     */
    void registerOrder(OrderRequestDTO orderRequestDTO, String correlationId);

    /**
     *
     * @param id - Input ID
     * @param orderRequestDTO - OrderRequestDTO Object
     * @param correlationId - UUID to trace the request
     * @return boolean indicating whether the product update was successful or not
     */
    boolean updateOrder(Long id, OrderRequestDTO orderRequestDTO, String correlationId);

    /**
     *
     * @param id - Input ID
     * @return boolean indicating whether the order deletion was successful or not
     */
    boolean deleteOrder(Long id);
}
