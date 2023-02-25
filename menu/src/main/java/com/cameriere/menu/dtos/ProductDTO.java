package com.cameriere.menu.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

public class ProductDTO {
	
	@NotBlank
	private String name;
	
	private BigDecimal price;
	
	private String imagePath;
	
	private boolean isActive = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
