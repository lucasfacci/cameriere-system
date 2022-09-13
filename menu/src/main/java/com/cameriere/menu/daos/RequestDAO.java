package com.cameriere.menu.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cameriere.menu.models.Request;

@Repository
public interface RequestDAO extends JpaRepository<Request, Integer> {

}
