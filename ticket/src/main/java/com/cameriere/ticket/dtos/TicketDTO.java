package com.cameriere.ticket.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class TicketDTO {

    @NotEmpty
    private List<String> products = new ArrayList<>();

    private boolean isActive;

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
