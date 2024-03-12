package com.cameriere.oauth.services;

import org.springframework.stereotype.Service;

import com.cameriere.oauth.dtos.UserDTO;
import com.cameriere.oauth.proxies.UserProxy;

@Service
public class UserService {

	private final UserProxy userProxy;
	
	public UserService(UserProxy userProxy) {
		this.userProxy = userProxy;
	}
	
	public UserDTO findUserByEmail(String email) {
		UserDTO user = userProxy.getUserByEmail(email).getBody();
		if (user == null) {
			throw new IllegalArgumentException("User not found.");
		}
		return user;
	}
}
