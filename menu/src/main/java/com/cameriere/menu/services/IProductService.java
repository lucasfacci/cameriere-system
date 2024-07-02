package com.cameriere.menu.services;

import com.cameriere.menu.dtos.ProductRequestDTO;
import com.cameriere.menu.dtos.ProductResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductService {

    /**
     *
     * @return All the products
     */
    List<ProductResponseDTO> listProducts();

    /**
     *
     * @param id - Input ID
     * @return A product based on a given ID
     */
    ProductResponseDTO getProduct(Long id);

    /**
     *
     * @param productRequestDTO - ProductRequestDTO Object
     * @param file - Product image file
     * @throws IOException IOException if there is an error reading from or writing to the file system
     */
    void registerProduct(ProductRequestDTO productRequestDTO, MultipartFile file) throws IOException;

    /**
     *
     * @param id - Input ID
     * @param productRequestDTO - ProductRequestDTO Object
     * @param file - Product image file
     * @return boolean indicating whether the product update was successful or not
     * @throws IOException IOException if there is an error reading from or writing to the file system
     */
    boolean updateProduct(Long id, ProductRequestDTO productRequestDTO, MultipartFile file) throws IOException;

    /**
     *
     * @param id - Input ID
     * @return boolean indicating whether the product deletion was successful or not
     */
    boolean deleteProduct(Long id);

    /**
     *
     * @param productIds - IDs of the products
     * @return boolean indicating if the product quantity was updated successfully or not
     */
    boolean decreaseProductsQuantity(List<Long> productIds);
}
