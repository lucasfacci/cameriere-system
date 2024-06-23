package com.cameriere.order.controllers;

import java.util.List;

import com.cameriere.order.constants.OrderConstants;
import com.cameriere.order.dtos.ErrorResponseDTO;
import com.cameriere.order.dtos.OrderRequestDTO;
import com.cameriere.order.dtos.ResponseDTO;
import com.cameriere.order.services.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cameriere.order.dtos.OrderResponseDTO;

import jakarta.validation.Valid;

@Tag(
		name = "CRUD REST API for Order in Cameriere System.",
		description = "CRUD REST API in Cameriere System to CREATE, UPDATE, GET and DELETE orders."
)
@RestController
@RequestMapping(path = "/orders", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	private final IOrderService iOrderService;

	@Operation(
			summary = "Get Orders REST API.",
			description = "REST API to get a list of orders from the Cameriere System."
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status OK."
	)
	@GetMapping
	public ResponseEntity<List<OrderResponseDTO>> listOrders() {
		logger.debug("listOrders() method invoked.");
		return ResponseEntity.status(HttpStatus.OK).body(iOrderService.listOrders());
	}

	@Operation(
			summary = "Get Order REST API.",
			description = "REST API to get a single order from the Cameriere System based on an ID."
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status OK."
	)
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOrder(@PathVariable
											   Long id) {
		logger.debug("getOrder() method start.");
		OrderResponseDTO orderResponseDTO = iOrderService.getOrder(id);
		logger.debug("getOrder() method end.");
		return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTO);
	}

	@Operation(
			summary = "Create Order REST API.",
			description = "REST API to create a new order in the Cameriere System."
	)
	@ApiResponse(
			responseCode = "201",
			description = "HTTP Status CREATED."
	)
	@PostMapping
	public ResponseEntity<ResponseDTO> registerOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
		logger.debug("registerOrder() method start.");
		iOrderService.registerOrder(orderRequestDTO);
		logger.debug("registerOrder() method end.");
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDTO(OrderConstants.STATUS_201, OrderConstants.MESSAGE_201));
	}

	@Operation(
			summary = "Update Order REST API.",
			description = "REST API to update an order in the Cameriere System based on a ID."
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
	public ResponseEntity<Object> updateOrder(@PathVariable
												  Long id,
											  @RequestBody @Valid OrderRequestDTO orderRequestDTO) {
		logger.debug("updateOrder() method start.");
		boolean isUpdated = iOrderService.updateOrder(id, orderRequestDTO);
		logger.debug("updateOrder() method end.");
		if (isUpdated) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDTO(OrderConstants.STATUS_200, OrderConstants.MESSAGE_200));
		} else {
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDTO(OrderConstants.STATUS_417, OrderConstants.MESSAGE_417_UPDATE));
		}
	}

	@Operation(
			summary = "Delete Order REST API.",
			description = "REST API to delete an order in the Cameriere System."
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
	public ResponseEntity<Object> deleteOrder(@PathVariable
												  Long id) {
		logger.debug("deleteOrder() method start.");
		boolean isDeleted = iOrderService.deleteOrder(id);
		logger.debug("deleteOrder() method end.");
		if (isDeleted) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDTO(OrderConstants.STATUS_200, OrderConstants.MESSAGE_200));
		} else {
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDTO(OrderConstants.STATUS_417, OrderConstants.MESSAGE_417_DELETE));
		}
	}
}