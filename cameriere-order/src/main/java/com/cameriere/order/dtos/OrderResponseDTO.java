package com.cameriere.order.dtos;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
		name = "OrderResponse",
		description = "Schema to hold Order response information."
)
@Data
public class OrderResponseDTO {

	@Schema(
			description = "Number of the table where the order was made.",
			example = "9"
	)
	private int tableNumber;

	@Schema(
			description = "Total price of the order.",
			example = "100.00"
	)
	private BigDecimal totalPrice;

	@Schema(
			description = "List of products."
	)
	private List<ProductDTO> products;
}