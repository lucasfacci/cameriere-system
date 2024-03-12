package com.cameriere.oauth.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cameriere.oauth.dtos.UserDTO;

@FeignClient(name = "cameriere-user")
public interface UserProxy {

	@GetMapping("/users/search")
	ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email);
}
