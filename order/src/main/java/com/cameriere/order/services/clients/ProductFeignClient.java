package com.cameriere.order.services.clients;

import com.cameriere.order.dtos.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "menu", fallback = ProductFallback.class)
public interface ProductFeignClient {

	@GetMapping(value = "/products/{id}", consumes = "application/json")
	ResponseEntity<ProductDTO> getProduct(@PathVariable Long id);
}