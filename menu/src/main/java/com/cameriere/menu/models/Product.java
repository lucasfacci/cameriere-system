package com.cameriere.menu.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

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
	
	@Schema(name = "image_path", description = "Product image path", required = true)
	private String image_path;
	
	@Schema(name = "created_at", description = "Product created timestamp")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime created_at = LocalDateTime.now();

	public Product() {
	}

	public Product(int id, @NotNull(message = "Enter the product name.") String name,
			@NotNull(message = "Enter the product price.") double price, String image_path, LocalDateTime created_at) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image_path = image_path;
		this.created_at = created_at;
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

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
}
