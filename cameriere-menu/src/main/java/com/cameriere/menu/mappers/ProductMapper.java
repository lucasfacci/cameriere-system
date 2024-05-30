package com.cameriere.menu.mappers;

import com.cameriere.menu.dtos.ProductDTORequest;
import com.cameriere.menu.dtos.ProductDTOResponse;
import com.cameriere.menu.models.Product;

public class ProductMapper {

    public static ProductDTOResponse mapToProductDTOResponseFromProduct(Product product, ProductDTOResponse productDTOResponse) {
        productDTOResponse.setName(product.getName());
        productDTOResponse.setPrice(product.getPrice());
        productDTOResponse.setImagePath(product.getImagePath());
        return productDTOResponse;
    }

    public static Product mapToProductFromProductDTORequest(ProductDTORequest productDTORequest, Product product) {
        product.setName(productDTORequest.getName());
        product.setPrice(productDTORequest.getPrice());
        return product;
    }
}
