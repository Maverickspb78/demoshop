package com.andreev.demoshop.service;

import com.andreev.demoshop.domain.Product;
import com.andreev.demoshop.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAll();
    void addToUserBucket(Long productId, String username);
    void addProduct(ProductDTO dto);
    Optional<Product> getProductById(Long id);
}