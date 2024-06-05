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
			example = "[\"607cee21-15be-4e41-8f89-8d0590444cb9\", \"c6841f28-7931-483b-b194-f0437f20a8be\"]"
	)
	private List<String> products;
}