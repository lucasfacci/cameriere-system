package com.cameriere.menu.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Schema(
		name = "ProductRequest",
		description = "Schema to hold Product request information."
)
@Data
public class ProductRequestDTO {

	@Schema(
			description = "Name of the product.",
			example = "Cappuccino"
	)
	@NotEmpty(message = "The name cannot be null or empty.")
	@Size(min = 1, max = 40, message = "The length of the product name should be between 1 and 40.")
	private String name;

	@Schema(
			description = "Quantity of products available.",
			example = "50"
	)
	@PositiveOrZero(message = "The quantity must be a positive number or zero.")
	private int quantity;

	@Schema(
			description = "Price of the product.",
			example = "5.55"
	)
	@NotNull(message = "The price cannot be null.")
	@DecimalMin(value = "0.0", inclusive = false, message = "The price must be greater than 0.")
	@Digits(integer = 5, fraction = 2, message = "The price must be a numeric value with up to 5 integer digits and 2 fraction digits.")
	private BigDecimal price;

	@Schema(
			description = "Indicates whether the product is sold out or not.",
			example = "false"
	)
	@NotNull(message = "The soldOut field must be provided.")
	private Boolean soldOut;

	@Schema(
			description = "Indicates whether the product is active or not.",
			example = "true"
	)
	private Boolean active;
}
