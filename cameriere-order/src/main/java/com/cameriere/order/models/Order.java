package com.cameriere.order.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tb_order")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Order extends Standard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private int tableNumber;

	private BigDecimal totalPrice;

	private List<String> products;
}