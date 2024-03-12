package com.cameriere.oauth.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cameriere.oauth.dtos.UserDTO;
import com.cameriere.oauth.services.UserService;

@RestController
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/users/search")
	public ResponseEntity<Object> getUserByEmail(@RequestParam String email) {
		try {
			UserDTO user = userService.findUserByEmail(email);
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
	}
}
