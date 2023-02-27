package com.cameriere.order.controllers;

import java.math.BigDecimal;
import java.util.List;

import com.cameriere.order.dtos.ProductDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
