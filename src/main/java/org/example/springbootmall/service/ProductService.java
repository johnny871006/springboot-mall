package org.example.springbootmall.service;

import org.example.springbootmall.dto.ProductRequest;
import org.example.springbootmall.model.Product;

public interface ProductService {
    Product getByProductId(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);
}
