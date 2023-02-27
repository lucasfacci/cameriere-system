package com.cameriere.order.services;

import java.util.List;

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
}
