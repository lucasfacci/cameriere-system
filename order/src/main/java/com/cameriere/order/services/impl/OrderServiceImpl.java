package com.cameriere.order.services.impl;

import com.cameriere.order.dtos.OrderRequestDTO;
import com.cameriere.order.dtos.OrderResponseDTO;
import com.cameriere.order.dtos.ProductDTO;
import com.cameriere.order.exceptions.ResourceNotFoundException;
import com.cameriere.order.mappers.OrderMapper;
import com.cameriere.order.models.Order;
import com.cameriere.order.proxies.ProductProxy;
import com.cameriere.order.repositories.OrderRepository;
import com.cameriere.order.services.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private OrderRepository orderRepository;
    private ProductProxy productProxy;

    /**
     * @return All the orders
     */
    @Override
    public List<OrderResponseDTO> listOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();

        for (Order order: orders) {
            List<ProductDTO> products = new ArrayList<>();
            for (Long productId: order.getProducts()) {
                ProductDTO productDTO = productProxy.getProduct(productId).getBody();
                products.add(productDTO);
            }
            OrderResponseDTO orderResponseDTO = OrderMapper.mapToOrderResponseDTOFromOrder(order, new OrderResponseDTO());
            orderResponseDTO.setProducts(products);
            orderResponseDTOs.add(orderResponseDTO);
        }

        return orderResponseDTOs;
    }

    /**
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
            ProductDTO productDTO = productProxy.getProduct(productId).getBody();
            products.add(productDTO);
        }

        OrderResponseDTO orderResponseDTO = OrderMapper.mapToOrderResponseDTOFromOrder(order, new OrderResponseDTO());
        orderResponseDTO.setProducts(products);
        return orderResponseDTO;
    }

    /**
     * @param orderRequestDTO - OrderRequestDTO Object
     */
    @Override
    public void registerOrder(OrderRequestDTO orderRequestDTO) {
        Order order = OrderMapper.mapToOrderFromOrderRequestDTO(orderRequestDTO, new Order());

        BigDecimal totalPrice = new BigDecimal("0.00");
        for (Long productId: orderRequestDTO.getProducts()) {
            ProductDTO product = productProxy.getProduct(productId).getBody();
            if (product.getActive()) {
                totalPrice = totalPrice.add(product.getPrice());
            } else {
                throw new ResourceNotFoundException("Product", "id", productId.toString());
//                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The product " + product.getName() + " is no longer available.");
            }
        }
        order.setTotalPrice(totalPrice);
//        orderProducer.send(order.getId() + ";" + order.getTotalPrice() + ";" + order.getCreatedAt() + ";" + order.getProducts());
        orderRepository.save(order);
    }

    /**
     * @param id              - Input ID
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
            ProductDTO product = productProxy.getProduct(productId).getBody();
            if (product.getActive()) {
                totalPrice = totalPrice.add(product.getPrice());
            } else {
                throw new ResourceNotFoundException("Product", "id", productId.toString());
            }
        }

        OrderMapper.mapToOrderFromOrderRequestDTO(orderRequestDTO, order);
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);
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
}