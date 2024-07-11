package org.example.springbootmall.service;

import org.example.springbootmall.constant.ProductCategory;
import org.example.springbootmall.dto.ProductRequest;
import org.example.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductCategory category,String search);

    Product getByProductId(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProduct(Integer productId);
}
