package com.cameriere.oauth.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.cameriere.oauth.models.User;

public interface UserRepository extends CrudRepository<User, UUID> {
	Optional<User> findByUsername(String username);
}