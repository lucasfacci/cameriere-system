package com.cameriere.order.dtos;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public class OrderDTO {
	
	@NotEmpty
    private List<String> products = new ArrayList<>();

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}
}
