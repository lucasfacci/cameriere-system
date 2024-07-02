package com.cameriere.order.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
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

	@Schema(
			description = "Status of the order (PENDING, CONFIRMED, PREPARING, READY, COMPLETED, CANCELLED).",
			example = "COMPLETED"
	)
	@Pattern(regexp = "^(PENDING|CONFIRMED|PREPARING|READY|COMPLETED|CANCELLED)$",
			message = "Invalid status. Must be one of: PENDING, CONFIRMED, PREPARING, READY, COMPLETED, CANCELLED.")
	private String status;

	@Schema(
			description = "Note about the order.",
			example = "I want the sauce without bacon."
	)
	@Size(min = 1, max = 140, message = "The length of the note should be between 1 and 140.")
	private String note;
}