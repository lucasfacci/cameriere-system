package com.cameriere.menu.functions;

import com.cameriere.menu.dtos.OrderMessageDTO;
import com.cameriere.menu.services.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;

@Configuration
public class MenuFunctions {

    private static final Logger log = LoggerFactory.getLogger(MenuFunctions.class);

    @Bean
    public Consumer<OrderMessageDTO> decreaseProductsQuantity(IProductService productService) {
        return orderMessageDTO -> {
            List<Long> productIds = orderMessageDTO.products();
            log.info("Updating the quantity of the products: " + productIds);
            productService.decreaseProductsQuantity(productIds);
        };
    }
}
