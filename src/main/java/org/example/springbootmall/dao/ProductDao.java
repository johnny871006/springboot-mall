package org.example.springbootmall.dao;

import org.example.springbootmall.dto.ProductRequest;
import org.example.springbootmall.model.Product;

public interface ProductDao {
    Product getByProductId(Integer productId);
    Integer createProduct(ProductRequest productRequest);
}
