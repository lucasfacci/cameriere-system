package com.cameriere.menu.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

@Schema(name = "Product", description = "Represents a product")
@Entity(name = "product")
public class Product {

	@Column(name = "id")
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

	public Product() {
	}

	public Product(int id, @NotNull(message = "Enter the product name.") String name,
			@NotNull(message = "Enter the product price.") double price, String image_path) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image_path = image_path;
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
}
