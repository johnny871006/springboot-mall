package org.example.springbootmall.dao;

import org.example.springbootmall.dto.ProductQueryParams;
import org.example.springbootmall.dto.ProductRequest;
import org.example.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    Integer getCount(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getByProductId(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void updateStock(Integer productId, Integer stock);

    void deleteProduct(Integer productId);
}
