package org.example.springbootmall.dao;

import org.example.springbootmall.model.Product;

public interface ProductDao {
    Product getByProductId(Integer productId);
}
