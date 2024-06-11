package com.cameriere.menu.models;

import java.math.BigDecimal;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "tb_product")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Product extends Standard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private int quantity;

	private BigDecimal price;
	
	private String imagePath;

	private Boolean soldOut;
}