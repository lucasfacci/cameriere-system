package com.cameriere.order.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Schema(
		name = "OrderRequest",
		description = "Schema to hold Order request information."
)
@Data
public class OrderRequestDTO {

	@Schema(
			description = "Number of the table where the order was made.",
			example = "9"
	)
	@NotNull(message = "The table number cannot be null.")
	@Positive(message = "The table number must be a positive number.")
	private int tableNumber;

	@Schema(
			description = "List of product IDs.",
			example = "[\"1\", \"4\"]"
	)
	private List<Long> products;
}