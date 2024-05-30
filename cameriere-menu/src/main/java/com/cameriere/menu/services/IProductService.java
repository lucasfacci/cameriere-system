package com.cameriere.menu.services;

import com.cameriere.menu.dtos.ProductDTORequest;
import com.cameriere.menu.dtos.ProductDTOResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IProductService {

    /**
     *
     * @return All the products
     */
    List<ProductDTOResponse> listProducts();

    /**
     *
     * @param id - Input ID
     * @return A product based on a given ID
     */
    ProductDTOResponse getProduct(String id);

    /**
     *
     * @param productDTORequest - ProductDTORequest Object
     * @param file - Product image file
     */
    void registerProduct(ProductDTORequest productDTORequest, MultipartFile file) throws IOException;

    /**
     *
     * @param id - Input ID
     * @param productDTORequest - ProductDTORequest Object
     * @param file - Product image file
     * @return boolean indicating if the update of Product details is successful or not
     * @throws IOException IOException if there is an error reading from or writing to the file system
     */
    boolean updateProduct(String id, ProductDTORequest productDTORequest, MultipartFile file) throws IOException;

    /**
     *
     * @param id - Input ID
     * @return boolean indicating if the delete of Product details is successful or not
     */
    boolean deleteProduct(String id);
}
