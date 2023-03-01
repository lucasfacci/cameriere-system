package com.cameriere.ticket.proxies;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "order")
public interface OrderProxy {
}
