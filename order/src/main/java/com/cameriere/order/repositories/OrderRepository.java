package com.cameriere.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cameriere.order.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}