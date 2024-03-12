package com.cameriere.user.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cameriere.user.models.User;
import com.cameriere.user.services.UserService;

@RestController
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable UUID id) {
		Optional<User> optionalUserModel = userService.findById(id);
		
		if (optionalUserModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optionalUserModel.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
	}
	
	@GetMapping("users/search")
	public ResponseEntity<Object> getUserByEmail(@RequestParam String email) {
		Optional<User> optionalUserModel = userService.findByEmail(email);
		
		if (optionalUserModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optionalUserModel.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
	}
}
