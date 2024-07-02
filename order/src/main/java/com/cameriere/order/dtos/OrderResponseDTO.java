package com.cameriere.order.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

	@Schema(
			description = "Status of the order (PENDING, CONFIRMED, PREPARING, READY, COMPLETED, CANCELLED).",
			example = "COMPLETED"
	)
	private String status;

	@Schema(
			description = "Note about the order.",
			example = "I want the sauce without bacon."
	)
	private String note;

	@Schema(
			description = "Timestamp of when the order status was changed to PENDING."
	)
	private LocalDateTime pendingTimestamp;

	@Schema(
			description = "Timestamp of when the order status was changed to CONFIRMED."
	)
	private LocalDateTime confirmedTimestamp;

	@Schema(
			description = "Timestamp of when the order status was changed to PREPARING."
	)
	private LocalDateTime preparingTimestamp;

	@Schema(
			description = "Timestamp of when the order status was changed to READY."
	)
	private LocalDateTime readyTimestamp;

	@Schema(
			description = "Timestamp of when the order status was changed to COMPLETED."
	)
	private LocalDateTime completedTimestamp;

	@Schema(
			description = "Timestamp of when the order status was changed to CANCELLED."
	)
	private LocalDateTime cancelledTimestamp;
}