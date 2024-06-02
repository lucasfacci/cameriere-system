package com.cameriere.menu.dtos;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
		name = "ProductResponse",
		description = "Schema to hold Product request information."
)
@Data
public class ProductResponseDTO {

	@Schema(
			description = "Name of the product.",
			example = "Cappuccino"
	)
	private String name;

	@Schema(
			description = "Quantity of products available.",
			example = "50"
	)
	private int quantity;

	@Schema(
			description = "Price of the product.",
			example = "5.55"
	)
	private BigDecimal price;

	@Schema(
			description = "Product image path.",
			example = "/home/user/Documents/cameriere-system/cameriere-menu/src/main/resources/static/images/cappuccino.jpg"
	)
	private String imagePath;

	@Schema(
			description = "Indicates whether the product is sold out or not.",
			example = "false"
	)
	private Boolean soldOut;

	@Schema(
			description = "Indicates whether the product is active or not.",
			example = "true"
	)
	private Boolean active;
}
