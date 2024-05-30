package com.cameriere.menu.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Entity
@Table(name = "tb_product")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Product extends Standard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String name;

	private BigDecimal price;
	
	private String imagePath;
}