package com.cameriere.order.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
	private Long id;

	private int tableNumber;

	private BigDecimal totalPrice;

	private List<Long> products;

	private String status;

	private String note;

	private LocalDateTime pendingTimestamp;

	private LocalDateTime confirmedTimestamp;

	private LocalDateTime preparingTimestamp;

	private LocalDateTime readyTimestamp;

	private LocalDateTime completedTimestamp;

	private LocalDateTime cancelledTimestamp;
}