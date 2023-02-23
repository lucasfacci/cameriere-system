package com.cameriere.ticket.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Schema(name = "Ticket", description = "Represents a ticket")
@Table(name = "ticket")
@Entity
public class Ticket {

	@Schema(name = "id", description = "Ticket id", accessMode = AccessMode.READ_ONLY)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Schema(name = "createdAt", description = "Ticket created timestamp")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@Schema(name = "products", description = "Products list", required = true)
	@ManyToMany
	@JoinTable(name = "ticket_product",
	    joinColumns = @JoinColumn(name = "ticket_id"),
	    inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> products = new ArrayList<>();
	
	@Schema(name = "isActive", description = "If the ticket is active")
	private boolean isActive = true;

	public Ticket() {
	}

	public Ticket(int id, LocalDateTime createdAt, List<Product> products, boolean isActive) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.products = products;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}