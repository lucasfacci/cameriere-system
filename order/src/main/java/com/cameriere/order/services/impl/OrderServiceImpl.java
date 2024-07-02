package com.cameriere.order.services.impl;

import com.cameriere.order.dtos.OrderMessageDTO;
import com.cameriere.order.dtos.OrderRequestDTO;
import com.cameriere.order.dtos.OrderResponseDTO;
import com.cameriere.order.dtos.ProductDTO;
import com.cameriere.order.exceptions.ResourceNotFoundException;
import com.cameriere.order.mappers.OrderMapper;
import com.cameriere.order.models.Order;
import com.cameriere.order.services.clients.ProductFeignClient;
import com.cameriere.order.repositories.OrderRepository;
import com.cameriere.order.services.IOrderService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderRepository orderRepository;
    private ProductFeignClient productFeignClient;
    private final StreamBridge streamBridge;

    /**
     *
     * @return All the orders
     */
    @Override
    public List<OrderResponseDTO> listOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();

        for (Order order: orders) {
            List<ProductDTO> products = new ArrayList<>();
            for (Long productId: order.getProducts()) {
                ResponseEntity<ProductDTO> productDTOResponseEntity = productFeignClient.getProduct(productId);
                if (null != productDTOResponseEntity) {
                    products.add(productDTOResponseEntity.getBody());
                }
            }

            OrderResponseDTO orderResponseDTO = OrderMapper.mapToOrderResponseDTOFromOrder(order, new OrderResponseDTO());
            orderResponseDTO.setProducts(products);
            orderResponseDTOs.add(orderResponseDTO);
        }

        return orderResponseDTOs;
    }

    /**
     *
     * @param id - Input ID
     * @return An order based on a given ID
     */
    @Override
    public OrderResponseDTO getOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order", "id", id.toString())
        );

        List<ProductDTO> products = new ArrayList<>();
        for (Long productId: order.getProducts()) {
            ResponseEntity<ProductDTO> productDTOResponseEntity = productFeignClient.getProduct(productId);
            if (null != productDTOResponseEntity) {
                products.add(productDTOResponseEntity.getBody());
            }
        }

        OrderResponseDTO orderResponseDTO = OrderMapper.mapToOrderResponseDTOFromOrder(order, new OrderResponseDTO());
        orderResponseDTO.setProducts(products);
        return orderResponseDTO;
    }

    /**
     *
     * @param orderRequestDTO - OrderRequestDTO Object
     */
    @Override
    public void registerOrder(OrderRequestDTO orderRequestDTO) {
        Order order = OrderMapper.mapToOrderFromOrderRequestDTO(orderRequestDTO, new Order());

        BigDecimal totalPrice = new BigDecimal("0.00");
        for (Long productId: orderRequestDTO.getProducts()) {
            ProductDTO productDTO = productFeignClient.getProduct(productId).getBody();
            if (productDTO.getActive()) {
                totalPrice = totalPrice.add(productDTO.getPrice());

            } else {
                throw new ResourceNotFoundException("Product", "id", productId.toString());
            }
        }

        order.setTotalPrice(totalPrice);
        String status = orderRequestDTO.getStatus();
        if (status != null) {
            order.setStatus(status);
            switch (status) {
                case "PENDING" -> order.setPendingTimestamp(LocalDateTime.now());
                case "CONFIRMED" -> order.setConfirmedTimestamp(LocalDateTime.now());
                case "PREPARING" -> order.setPreparingTimestamp(LocalDateTime.now());
                case "READY" -> order.setReadyTimestamp(LocalDateTime.now());
                case "COMPLETED" -> order.setCompletedTimestamp(LocalDateTime.now());
                case "CANCELLED" -> order.setCancelledTimestamp(LocalDateTime.now());
            }
        } else {
            order.setStatus("PENDING");
            order.setPendingTimestamp(LocalDateTime.now());
        }

        orderRepository.save(order);
    }

    /**
     *
     * @param id - Input ID
     * @param orderRequestDTO - OrderRequestDTO Object
     * @return boolean indicating whether the product update was successful or not
     */
    @Override
    public boolean updateOrder(Long id, OrderRequestDTO orderRequestDTO) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order", "id", id.toString())
        );

        BigDecimal totalPrice = new BigDecimal("0.00");
        for (Long productId: orderRequestDTO.getProducts()) {
            ProductDTO productDTO = productFeignClient.getProduct(productId).getBody();
            if (productDTO.getActive()) {
                totalPrice = totalPrice.add(productDTO.getPrice());
            } else {
                throw new ResourceNotFoundException("Product", "id", productId.toString());
            }
        }

        OrderMapper.mapToOrderFromOrderRequestDTO(orderRequestDTO, order);
        order.setTotalPrice(totalPrice);
        String status = orderRequestDTO.getStatus();
        if (status != null) {
            order.setStatus(status);
            switch (status) {
                case "PENDING" -> order.setPendingTimestamp(LocalDateTime.now());
                case "CONFIRMED" -> order.setConfirmedTimestamp(LocalDateTime.now());
                case "PREPARING" -> order.setPreparingTimestamp(LocalDateTime.now());
                case "READY" -> order.setReadyTimestamp(LocalDateTime.now());
                case "COMPLETED" -> order.setCompletedTimestamp(LocalDateTime.now());
                case "CANCELLED" -> order.setCancelledTimestamp(LocalDateTime.now());
            }
        }

        Order savedOrder = orderRepository.save(order);
        if (savedOrder.getStatus().equals("READY")) {
            sendCommunication(savedOrder);
        }

        return true;
    }

    /**
     * @param id - Input ID
     * @return boolean indicating whether the product deletion was successful or not
     */
    @Override
    public boolean deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order", "id", id.toString())
        );

        orderRepository.deleteById(order.getId());
        return true;
    }

    private void sendCommunication(Order order) {
        var orderMessageDTO = new OrderMessageDTO(order.getTableNumber(), order.getTotalPrice(), order.getProducts(), order.getStatus(), order.getNote());

        log.info("Sending communication request for the details: {}", orderMessageDTO);
        var result = streamBridge.send("sendCommunication-out-0", orderMessageDTO);
        log.info("Is the communication request successfully triggered? : {}", result);
    }
}