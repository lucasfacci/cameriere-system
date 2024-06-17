package com.cameriere.order.services.clients;

import com.cameriere.order.dtos.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductFallback implements ProductFeignClient {

    @Override
    public ResponseEntity<ProductDTO> getProduct(String correlationId, Long id) {
        return null;
    }
}
