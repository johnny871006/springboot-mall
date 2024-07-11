package org.example.springbootmall.service.impl;

import org.example.springbootmall.dao.ProductDao;
import org.example.springbootmall.dto.ProductQueryParams;
import org.example.springbootmall.dto.ProductRequest;
import org.example.springbootmall.model.Product;
import org.example.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer getCount(ProductQueryParams productQueryParams) {
        return productDao.getCount(productQueryParams);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Product getByProductId(Integer productId) {
        return productDao.getByProductId(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }
}
