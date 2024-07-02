package com.cameriere.message.functions;

import com.cameriere.message.dtos.OrderMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<OrderMessageDTO, OrderMessageDTO> notification() {
        return orderMessageDTO -> {
            log.info("Sending notification with the details: " + orderMessageDTO.toString());
            return orderMessageDTO;
        };
    }

}
