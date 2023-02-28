package com.cameriere.order.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.cameriere.order.dtos.ProductDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cameriere.order.dtos.OrderDTO;
import com.cameriere.order.models.Order;
import com.cameriere.order.proxies.ProductProxy;
import com.cameriere.order.services.OrderService;

import jakarta.validation.Valid;

@RestController
public class OrderController {

	private final OrderService orderService;
	private final ProductProxy productProxy;	

	public OrderController(OrderService orderService, ProductProxy productProxy) {
		this.orderService = orderService;
		this.productProxy = productProxy;
	}
	
	@GetMapping("/orders")
	public ResponseEntity<List<Order>> listOrders() {
		return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/orders/{id}")
	public ResponseEntity<Object> getOrder(@PathVariable UUID id) {
		Optional<Order> optionalOrderModel = orderService.findById(id);

		if (optionalOrderModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optionalOrderModel.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
		}
	}

	@PostMapping("/orders")
	public ResponseEntity<Object> saveOrder(@RequestBody @Valid OrderDTO orderDTO) {
		var orderModel = new Order();
		List<String> productsIds = orderDTO.getProducts();
		BeanUtils.copyProperties(orderDTO, orderModel);

		BigDecimal totalPrice = new BigDecimal("0.00");
		for (int i = 0; i < productsIds.size(); i++) {
			ProductDTO product = productProxy.getProduct(productsIds.get(i)).getBody();
			if (product.getIsActive()) {
				totalPrice = totalPrice.add(product.getPrice());
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The product " + product.getName() + " is no longer available.");
			}
		}
		orderModel.setTotalPrice(totalPrice);
		return ResponseEntity.status(HttpStatus.OK).body(orderService.save(orderModel));
	}

	@PutMapping("/orders/{id}")
	public ResponseEntity<Object> updateOrder(@PathVariable UUID id, @RequestBody @Valid OrderDTO orderDTO) {
		Optional<Order> optionalOrderModel = orderService.findById(id);

		if (optionalOrderModel.isPresent()) {
			List<String> productsIds = orderDTO.getProducts();
			Order orderModel = optionalOrderModel.get();
			BeanUtils.copyProperties(orderDTO, orderModel);

			BigDecimal totalPrice = new BigDecimal("0.00");
			for (int i = 0; i < productsIds.size(); i++) {
				ProductDTO product = productProxy.getProduct(productsIds.get(i)).getBody();
				if (product.getIsActive()) {
					totalPrice = totalPrice.add(product.getPrice());
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The product " + product.getName() + " is no longer available.");
				}
			}
			orderModel.setTotalPrice(totalPrice);
			return ResponseEntity.status(HttpStatus.OK).body(orderService.save(orderModel));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
		}
	}

	@DeleteMapping("/orders/{id}")
	public ResponseEntity<Object> deleteOrder(@PathVariable UUID id) {
		Optional<Order> optionalOrderModel = orderService.findById(id);

		if (optionalOrderModel.isPresent()) {
			Order orderModel = optionalOrderModel.get();
			orderService.delete(orderModel);

			return ResponseEntity.status(HttpStatus.OK).body("Order successfully deleted.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
		}
	}
}
