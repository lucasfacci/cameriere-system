package com.cameriere.menu.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cameriere.menu.models.Product;
import com.cameriere.menu.repositories.ProductRepository;

@Service
public class ProductService {

	final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Transactional
	public Product save(Product productModel) {
		return productRepository.save(productModel);
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Optional<Product> findById(UUID id) {
		return productRepository.findById(id);
	}

	@Transactional
	public void delete(Product productModel) {
		productRepository.delete(productModel);
	}	
}
