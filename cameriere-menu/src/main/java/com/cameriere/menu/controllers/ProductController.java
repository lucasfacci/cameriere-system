package com.cameriere.menu.controllers;

import java.io.IOException;
import java.util.List;

import com.cameriere.menu.constants.ProductConstants;
import com.cameriere.menu.dtos.ErrorResponseDTO;
import com.cameriere.menu.dtos.ProductDTORequest;
import com.cameriere.menu.dtos.ResponseDTO;
import com.cameriere.menu.services.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cameriere.menu.dtos.ProductDTOResponse;

@Tag(
		name = "CRUD REST API for Menu in Cameriere System.",
		description = "CRUD REST API in Cameriere System to CREATE, UPDATE, GET and DELETE menu products."
)
@RestController
@RequestMapping(path = "/products", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class ProductController {

	private IProductService iProductService;

	@Operation(
			summary = "Get Products REST API.",
			description = "REST API to get a list of products from the Cameriere System."
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status OK."
	)
	@GetMapping
	public ResponseEntity<List<ProductDTOResponse>> listProducts() {
		List<ProductDTOResponse> productDTOResponses = iProductService.listProducts();
		return ResponseEntity.status(HttpStatus.OK).body(productDTOResponses);
	}

	@Operation(
			summary = "Get Product REST API.",
			description = "REST API to get a single product from the Cameriere System based on a UUID."
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status OK."
	)
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTOResponse> getProduct(@PathVariable
															 @Pattern(regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}",
																	 message = "The id must be a valid UUID.")
															 String id) {
		ProductDTOResponse productDTOResponse = iProductService.getProduct(id);
		return ResponseEntity.status(HttpStatus.OK).body(productDTOResponse);
	}

	@Operation(
			summary = "Create Product REST API.",
			description = "REST API to create a new product in the Cameriere System."
	)
	@ApiResponse(
			responseCode = "201",
			description = "HTTP Status CREATED."
	)
	@PostMapping
	public ResponseEntity<ResponseDTO> registerProduct(@Valid ProductDTORequest productDTORequest,
													   @RequestParam MultipartFile file) throws IOException {
		iProductService.registerProduct(productDTORequest, file);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDTO(ProductConstants.STATUS_201, ProductConstants.MESSAGE_201));
	}

	@Operation(
			summary = "Update Product REST API.",
			description = "REST API to update a product in the Cameriere System based on a UUID."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "HTTP Status OK."
			),
			@ApiResponse(
					responseCode = "417",
					description = "Expectation Failed."
			),
			@ApiResponse(
					responseCode = "500",
					description = "HTTP Status INTERNAL SERVER ERROR.",
					content = @Content(
							schema = @Schema(implementation = ErrorResponseDTO.class)
					)
			)
	})
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO> updateProduct(@PathVariable
														 @Pattern(regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}",
																 message = "The id must be a valid UUID.")
														 String id,
													 @Valid ProductDTORequest productDTORequest,
													 @RequestParam(required = false) MultipartFile file) throws IOException {
		boolean isUpdated = iProductService.updateProduct(id, productDTORequest, file);
		if (isUpdated) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDTO(ProductConstants.STATUS_200, ProductConstants.MESSAGE_200));
		} else {
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDTO(ProductConstants.STATUS_417, ProductConstants.MESSAGE_417_UPDATE));
		}
	}

	@Operation(
			summary = "Delete Product REST API.",
			description = "REST API to delete a product in the Cameriere System."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "HTTP Status OK."
			),
			@ApiResponse(
					responseCode = "417",
					description = "Expectation Failed."
			),
			@ApiResponse(
					responseCode = "500",
					description = "HTTP Status INTERNAL SERVER ERROR.",
					content = @Content(
							schema = @Schema(implementation = ErrorResponseDTO.class)
					)
			)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable
														 @Pattern(regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}",
																 message = "The id must be a valid UUID.")
														 String id) {
		boolean isDeleted = iProductService.deleteProduct(id);
		if (isDeleted) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDTO(ProductConstants.STATUS_200, ProductConstants.MESSAGE_200));
		} else {
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDTO(ProductConstants.STATUS_417, ProductConstants.MESSAGE_417_DELETE));
		}
	}
}