package org.example.springbootmall.service;

import org.example.springbootmall.model.Product;

public interface ProductService {
    Product getByProductId(Integer productId);
}