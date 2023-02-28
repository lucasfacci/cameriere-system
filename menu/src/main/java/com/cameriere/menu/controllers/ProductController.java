package com.cameriere.menu.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cameriere.menu.dtos.ProductDTO;
import com.cameriere.menu.models.Product;
import com.cameriere.menu.services.ProductService;

@RestController
public class ProductController {
	
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> listProducts() {
		return new ResponseEntity<List<Product>>(productService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Object> getProduct(@PathVariable UUID id) {
		Optional<Product> optionalProductModel = productService.findById(id);
		
		if (optionalProductModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optionalProductModel.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
	}
	
	@PostMapping("/products")
	public ResponseEntity<Object> registerProduct(@Valid ProductDTO productDTO, @RequestParam MultipartFile file) throws IOException {
		String uploadDirectory = "/home/lucas/Documentos/Workspace/cameriere-system/menu/src/main/resources/static/images";
		
		try {
			var productModel = new Product();
			StringBuilder fileName = new StringBuilder();
			Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
			fileName.append(file.getOriginalFilename());
			Files.write(fileNameAndPath, file.getBytes());
			productModel.setName(productDTO.getName());
			productModel.setPrice(productDTO.getPrice());
			productModel.setImagePath(fileNameAndPath.toString());

			return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
		} catch (IOException e) {
			throw new RuntimeException("It wasn't possible to upload the file.");
		}
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable UUID id, @Valid ProductDTO productDTO, @RequestParam(required = false) MultipartFile file) throws IOException {
		String uploadDirectory = "/home/lucas/Documentos/Workspace/cameriere-system/menu/src/main/resources/static/images";
		Optional<Product> optionalProductModel = productService.findById(id);
		
		if (optionalProductModel.isPresent()) {
			Product productModel = optionalProductModel.get();
			productModel.setName(productDTO.getName());
			productModel.setPrice(productDTO.getPrice());
			productModel.setIsActive(productDTO.getIsActive());
			
			if (file != null) {
				File oldFile = new File(productModel.getImagePath());
				oldFile.delete();
				StringBuilder fileName = new StringBuilder();
				Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
				fileName.append(file.getOriginalFilename());
				Files.write(fileNameAndPath, file.getBytes());
				productModel.setImagePath(fileNameAndPath.toString());
			}

			return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable UUID id) {
		Optional<Product> optionalProductModel = productService.findById(id);
		
		if (optionalProductModel.isPresent()) {
			Product productModel = optionalProductModel.get();
			File oldFile = new File(productModel.getImagePath());
			oldFile.delete();
			productService.delete(productModel);

			return ResponseEntity.status(HttpStatus.OK).body("Product successfully deleted.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
	}
}
