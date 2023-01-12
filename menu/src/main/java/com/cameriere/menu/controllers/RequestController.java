package com.cameriere.menu.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cameriere.menu.daos.ProductDAO;
import com.cameriere.menu.daos.RequestDAO;
import com.cameriere.menu.dtos.RequestDTO;
import com.cameriere.menu.models.Request;
import com.cameriere.menu.models.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class RequestController {

	@Autowired
	RequestDAO requestDao;
	
	@Autowired
	ProductDAO productDao;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listed requests", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Request.class)) }) })
	@Operation(summary = "List all the requests", description = "Get the list of all requests", tags = { "requests" })
	@GetMapping("/requests")
	public ResponseEntity<Iterable<Request>> listRequests() {
		return new ResponseEntity<Iterable<Request>>(requestDao.findAll(), HttpStatus.OK);
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Request found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Request.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid request id", content = @Content),
			@ApiResponse(responseCode = "404", description = "Request not found", content = @Content) })
	@Operation(summary = "Get a request", description = "Search a request by its id", tags = { "requests" })
	@GetMapping("/requests/{id}")
	public ResponseEntity<Object> getRequest(@PathVariable int id) {
		Optional<Request> optionalRequestBean = requestDao.findById(id);
		
		if (optionalRequestBean.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optionalRequestBean.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found.");
		}
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Request registered", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Request.class)) }),
			@ApiResponse(responseCode = "400", description = "Error when registering the request", content = @Content) })
	@Operation(summary = "Register a request", description = "Register a new request", tags = { "requests" })
	@PostMapping("/requests")
	public ResponseEntity<Object> registerRequest(@RequestBody @Valid RequestDTO requestDTO) {
 		Request request = new Request();

 		List<Product> requestProducts = productDao.findAllById(requestDTO.getProductIds());
 		request.setProducts(requestProducts);
 		
 		double totalPrice = 0;
 		for (int i = 0; i < requestProducts.size(); i++) {
 			totalPrice += requestProducts.get(i).getPrice();
 		}
 		request.setTotalPrice(totalPrice);
 		
 		requestDao.save(request);
		return ResponseEntity.status(HttpStatus.OK).body("Request successfully created.");
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Request updated", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Request.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid request id", content = @Content),
			@ApiResponse(responseCode = "404", description = "Request not found", content = @Content) })
	@Operation(summary = "Update a request", description = "Update a request by its id", tags = { "requests" })
	@PutMapping("/requests/{id}")
	public ResponseEntity<Object> updateRequest(@PathVariable int id, @RequestBody @Valid RequestDTO requestDTO) {
		Optional<Request> optionalRequestBean = requestDao.findById(id);
		
		if (optionalRequestBean.isPresent()) {
			Request requestBean = optionalRequestBean.get();
			
			List<Product> requestProducts = productDao.findAllById(requestDTO.getProductIds());
			
			requestBean.setProducts(requestProducts);
			
			double totalPrice = 0;
			for (int i = 0; i < requestProducts.size(); i++) {
				totalPrice += requestProducts.get(i).getPrice();
			}
			requestBean.setTotalPrice(totalPrice);
			
			requestDao.save(requestBean);
			return ResponseEntity.status(HttpStatus.OK).body("Request successfully updated.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found.");
		}
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Request deleted", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Request.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid request id", content = @Content),
			@ApiResponse(responseCode = "404", description = "Request not found", content = @Content) })
	@Operation(summary = "Delete a request", description = "Delete a request by its id", tags = { "requests" })
	@DeleteMapping("/requests/{id}")
	public ResponseEntity<Object> deleteRequest(@PathVariable int id) {
		Optional<Request> optionalRequestBean = requestDao.findById(id);
		
		if (optionalRequestBean.isPresent()) {
			requestDao.delete(optionalRequestBean.get());
			return ResponseEntity.status(HttpStatus.OK).body("Request successfully deleted.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found.");
		}
	}
}
