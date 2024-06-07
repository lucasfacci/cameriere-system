package com.cameriere.order.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(
        name = "Product",
        description = "Schema to hold Product information."
)
@Data
public class ProductDTO {

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