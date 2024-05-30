package com.cameriere.menu.dtos;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
		name = "ProductResponse",
		description = "Schema to hold Product request information."
)
@Data
public class ProductDTOResponse {

	@Schema(
			description = "Name of the product.",
			example = "Cappuccino"
	)
	private String name;

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
}
