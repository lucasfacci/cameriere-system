package com.cameriere.order.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cameriere.order.models.Order;
import com.cameriere.order.repositories.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional
	public Order save(Order orderModel) {
		return orderRepository.save(orderModel);
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

    public Optional<Order> findById(UUID id) {
		return orderRepository.findById(id);
    }

	@Transactional
    public void delete(Order orderModel) {
		orderRepository.delete(orderModel);
    }
}
