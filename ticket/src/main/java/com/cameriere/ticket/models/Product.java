package com.cameriere.ticket.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Product", description = "Represents a product")
@Table(name = "product")
@Entity
public class Product {

	@Schema(name = "id", description = "Product id", accessMode = AccessMode.READ_ONLY)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Schema(name = "name", description = "Product name", required = true, example = "Water bottle")
	@NotNull(message = "Enter the product name.")
	private String name;
	
	@Schema(name = "price", description = "Product price", required = true, example = "5")
	@NotNull(message = "Enter the product price.")
	private double price;
	
	@Schema(name = "imagePath", description = "Product image path", required = true)
	private String imagePath;
	
	@Schema(name = "createdAt", description = "Product created timestamp")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@Schema(name = "isActive", description = "If the product is active")
	private boolean isActive = true;

	public Product() {
	}

	public Product(int id, @NotNull(message = "Enter the product name.") String name,
			@NotNull(message = "Enter the product price.") double price, String imagePath, LocalDateTime createdAt,
			boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.imagePath = imagePath;
		this.createdAt = createdAt;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}