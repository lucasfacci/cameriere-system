package com.cameriere.menu.services.impl;

import com.cameriere.menu.constants.ProductConstants;
import com.cameriere.menu.dtos.ProductDTORequest;
import com.cameriere.menu.dtos.ProductDTOResponse;
import com.cameriere.menu.exceptions.ResourceNotFoundException;
import com.cameriere.menu.exceptions.UnableToDeleteFileException;
import com.cameriere.menu.exceptions.UnableToUploadFileException;
import com.cameriere.menu.mappers.ProductMapper;
import com.cameriere.menu.services.IProductService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.cameriere.menu.models.Product;
import com.cameriere.menu.repositories.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {

	private ProductRepository productRepository;

	/**
	 * @return All the products
	 */
	@Override
	public List<ProductDTOResponse> listProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductDTOResponse> productDTOResponses = new ArrayList<ProductDTOResponse>();
		for (Product product : products) {
			productDTOResponses.add(ProductMapper.mapToProductDTOResponseFromProduct(product, new ProductDTOResponse()));
		}
		return productDTOResponses;
	}

	/**
	 * @param id - Input ID
	 * @return A product based on a given ID
	 */
	@Override
	public ProductDTOResponse getProduct(String id) {
		Product product = productRepository.findById(UUID.fromString(id)).orElseThrow(
				() -> new ResourceNotFoundException("Product", "id", id)
		);

        return ProductMapper.mapToProductDTOResponseFromProduct(product, new ProductDTOResponse());
	}

	/**
	 *
	 * @param productDTORequest - ProductDTORequest Object
	 * @param file - Product image file
	 */
	@Override
	public void registerProduct(ProductDTORequest productDTORequest, MultipartFile file) throws IOException {
		Product product = ProductMapper.mapToProductFromProductDTORequest(productDTORequest, new Product());

		String uploadDirectory = ProductConstants.IMAGES_LOCATION;
		Path uploadPath = Paths.get(uploadDirectory);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try {
			Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
			Files.write(fileNameAndPath, file.getBytes());
			product.setImagePath(fileNameAndPath.toString());
		} catch (IOException e) {
			throw new UnableToUploadFileException("It wasn't possible to upload the file.");
		}

		product.setActive(true);
		productRepository.save(product);
	}

	/**
	 * @param id - Input ID
	 * @param productDTORequest - ProductDTORequest Object
	 * @param file - Product image file
	 * @return boolean indicating if the update of Product details is successful or not
	 * @throws IOException IOException if there is an error reading from or writing to the file system
	 */
	@Override
	public boolean updateProduct(String id, ProductDTORequest productDTORequest, MultipartFile file) throws IOException {
		String uploadDirectory = ProductConstants.IMAGES_LOCATION;

		Product product = productRepository.findById(UUID.fromString(id)).orElseThrow(
				() -> new ResourceNotFoundException("Product", "id", id)
		);

		if (file != null) {
			File oldFile = new File(product.getImagePath());
			boolean deleted = oldFile.delete();
			if (!deleted) {
				throw new UnableToDeleteFileException("It wasn't possible to delete the file.");
			}

			Path fileNameAndPath = null;
			try {
				fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				throw new UnableToUploadFileException("It wasn't possible to upload the file.");
			}

			ProductMapper.mapToProductFromProductDTORequest(productDTORequest, product);
			product.setImagePath(fileNameAndPath.toString());
		}

		productRepository.save(product);
		return true;
	}

	/**
	 * @param id - Input ID
	 * @return boolean indicating if the delete of Product details is successful or not
	 */
	@Override
	public boolean deleteProduct(String id) {
		Product product = productRepository.findById(UUID.fromString(id)).orElseThrow(
				() -> new ResourceNotFoundException("Product", "id", id)
		);

		File oldFile = new File(product.getImagePath());
		boolean deleted = oldFile.delete();
		if (!deleted) {
			throw new UnableToDeleteFileException("It wasn't possible to delete the file.");
		}

		productRepository.deleteById(product.getId());
		return true;
	}
}
