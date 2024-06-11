package com.cameriere.order.services;

import com.cameriere.order.dtos.OrderRequestDTO;
import com.cameriere.order.dtos.OrderResponseDTO;

import java.util.List;

public interface IOrderService {

    /**
     *
     * @return All the orders
     */
    List<OrderResponseDTO> listOrders();

    /**
     *
     * @param id - Input ID
     * @return An order based on a given ID
     */
    OrderResponseDTO getOrder(Long id);

    /**
     *
     * @param orderRequestDTO - OrderRequestDTO Object
     */
    void registerOrder(OrderRequestDTO orderRequestDTO);

    /**
     *
     * @param id - Input ID
     * @param orderRequestDTO - OrderRequestDTO Object
     * @return boolean indicating whether the product update was successful or not
     */
    boolean updateOrder(Long id, OrderRequestDTO orderRequestDTO);

    /**
     *
     * @param id - Input ID
     * @return boolean indicating whether the order deletion was successful or not
     */
    boolean deleteOrder(Long id);
}
