package com.cameriere.menu.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "tb_product")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Product extends Standard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String name;

	private int quantity;

	private BigDecimal price;
	
	private String imagePath;

	private Boolean soldOut;
}