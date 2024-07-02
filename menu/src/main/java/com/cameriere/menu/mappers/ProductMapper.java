package com.cameriere.menu.mappers;

import com.cameriere.menu.dtos.ProductRequestDTO;
import com.cameriere.menu.dtos.ProductResponseDTO;
import com.cameriere.menu.models.Product;

public class ProductMapper {

    public static ProductResponseDTO mapToProductResponseDTOFromProduct(Product product, ProductResponseDTO productResponseDTO) {
        productResponseDTO.setName(product.getName());
        productResponseDTO.setQuantity(product.getQuantity());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setImagePath(product.getImagePath());
        productResponseDTO.setSoldOut(product.getSoldOut());
        productResponseDTO.setActive(product.getActive());
        return productResponseDTO;
    }

    public static Product mapToProductFromProductRequestDTO(ProductRequestDTO productRequestDTO, Product product) {
        product.setName(productRequestDTO.getName());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setPrice(productRequestDTO.getPrice());
        product.setSoldOut(productRequestDTO.getSoldOut());
        product.setActive(productRequestDTO.getActive());
        return product;
    }
}
