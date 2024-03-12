package com.cameriere.user.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cameriere.user.models.User;
import com.cameriere.user.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}
	
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
