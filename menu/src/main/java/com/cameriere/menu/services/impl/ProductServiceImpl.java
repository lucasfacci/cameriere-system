package com.cameriere.menu.services.impl;

import com.cameriere.menu.constants.ProductConstants;
import com.cameriere.menu.dtos.ProductRequestDTO;
import com.cameriere.menu.dtos.ProductResponseDTO;
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
	public List<ProductResponseDTO> listProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();

		for (Product product : products) {
			ProductResponseDTO productResponseDTO = ProductMapper.mapToProductResponseDTOFromProduct(product, new ProductResponseDTO());
			productResponseDTOs.add(productResponseDTO);
		}

		return productResponseDTOs;
	}

	/**
	 * @param id - Input ID
	 * @return A product based on a given ID
	 */
	@Override
	public ProductResponseDTO getProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Product", "id", id.toString())
		);

        return ProductMapper.mapToProductResponseDTOFromProduct(product, new ProductResponseDTO());
	}

	/**
	 *
	 * @param productRequestDTO - ProductRequestDTO Object
	 * @param file - Product image file
	 * @throws IOException IOException if there is an error reading from or writing to the file system
	 */
	@Override
	public void registerProduct(ProductRequestDTO productRequestDTO, MultipartFile file) throws IOException {
		Product product = ProductMapper.mapToProductFromProductRequestDTO(productRequestDTO, new Product());

		if (productRequestDTO.getActive() == null) {
			product.setActive(true);
		}

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

		productRepository.save(product);
	}

	/**
	 * @param id - Input ID
	 * @param productRequestDTO - ProductRequestDTO Object
	 * @param file - Product image file
	 * @return boolean indicating whether the product update was successful or not
	 * @throws IOException IOException if there is an error reading from or writing to the file system
	 */
	@Override
	public boolean updateProduct(Long id, ProductRequestDTO productRequestDTO, MultipartFile file) throws IOException {
		String uploadDirectory = ProductConstants.IMAGES_LOCATION;

		Product product = productRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Product", "id", id.toString())
		);

		Boolean active = true;
		if (productRequestDTO.getActive() == null) {
			 active = product.getActive();
		}

		if (file != null) {
			File oldFile = new File(product.getImagePath());
			boolean deleted = oldFile.delete();
			if (!deleted) {
				throw new UnableToDeleteFileException("It wasn't possible to delete the file.");
			}

			Path fileNameAndPath;
			try {
				fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				throw new UnableToUploadFileException("It wasn't possible to upload the file.");
			}

			ProductMapper.mapToProductFromProductRequestDTO(productRequestDTO, product);
			product.setImagePath(fileNameAndPath.toString());
		}

		if (productRequestDTO.getActive() == null) {
			product.setActive(active);
		}

		productRepository.save(product);
		return true;
	}

	/**
	 * @param id - Input ID
	 * @return boolean indicating whether the product deletion was successful or not
	 */
	@Override
	public boolean deleteProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Product", "id", id.toString())
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
