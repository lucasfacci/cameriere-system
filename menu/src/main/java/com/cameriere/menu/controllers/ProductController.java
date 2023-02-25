package com.cameriere.menu.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class ProductController {
	
	ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listed products", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }) })
	@Operation(summary = "List all the products", description = "Get the list of all products", tags = { "products" })
	@GetMapping("/products")
	public ResponseEntity<List<Product>> listProducts() {
		return new ResponseEntity<List<Product>>(productService.findAll(), HttpStatus.OK);
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid product id", content = @Content),
			@ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })
	@Operation(summary = "Get a product", description = "Search a product by its id", tags = { "products" })
	@GetMapping("/products/{id}")
	public ResponseEntity<Object> getProduct(@PathVariable UUID id) {
		Optional<Product> optionalProductModel = productService.findById(id);
		
		if (optionalProductModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optionalProductModel.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product registered", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "400", description = "Error when registering the product", content = @Content) })
	@Operation(summary = "Register a product", description = "Register a new product", tags = { "products" })
	@PostMapping("/products")
	public ResponseEntity<Object> registerProduct(@Valid ProductDTO productDTO, @RequestParam MultipartFile file) throws IOException {
		String uploadDirectory = "/home/lucas/Documentos/Workspace/cameriere-system/menu/src/main/resources/static/images";
		
		try {
			var productModel = new Product();
			BeanUtils.copyProperties(productDTO, productModel);
			StringBuilder fileName = new StringBuilder();
			Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
			fileName.append(file.getOriginalFilename());
			Files.write(fileNameAndPath, file.getBytes());
			productModel.setImagePath(fileNameAndPath.toString());
			productService.save(productModel);
			return ResponseEntity.status(HttpStatus.OK).body("Product successfully created.");
		} catch (IOException e) {
			throw new RuntimeException("It wasn't possible to upload the file.");
		}
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product updated", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid product id", content = @Content),
			@ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })
	@Operation(summary = "Update a product", description = "Update a product by its id", tags = { "products" })
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
			
			productService.save(productModel);
			return ResponseEntity.status(HttpStatus.OK).body("Product successfully updated.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product deleted", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid product id", content = @Content),
			@ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })
	@Operation(summary = "Delete a product", description = "Delete a product by its id", tags = { "products" })
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
