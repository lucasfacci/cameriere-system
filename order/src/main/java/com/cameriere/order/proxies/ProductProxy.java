package com.cameriere.order.proxies;

import com.cameriere.order.dtos.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "menu")
public interface ProductProxy {

	@GetMapping("/products/{id}")
	ResponseEntity<ProductDTO> getProduct(@PathVariable Long id);
}