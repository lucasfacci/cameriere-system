package com.cameriere.menu.dtos;

import java.util.ArrayList;
import java.util.List;

public class RequestDTO {
	
    private List<Integer> productIds = new ArrayList<>();
    
	public RequestDTO() {

	}

	public RequestDTO(List<Integer> productIds) {
		this.productIds = productIds;
	}

	public List<Integer> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Integer> productIds) {
		this.productIds = productIds;
	}
}