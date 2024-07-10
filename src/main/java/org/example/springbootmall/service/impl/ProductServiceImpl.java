package org.example.springbootmall.service.impl;

import org.example.springbootmall.dao.ProductDao;
import org.example.springbootmall.model.Product;
import org.example.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getByProductId(Integer productId) {
        return productDao.getByProductId(productId);
    }
}
