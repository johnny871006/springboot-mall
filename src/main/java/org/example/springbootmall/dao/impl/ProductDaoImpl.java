package org.example.springbootmall.dao.impl;

import org.example.springbootmall.constant.ProductCategory;
import org.example.springbootmall.dao.ProductDao;
import org.example.springbootmall.dto.ProductQueryParams;
import org.example.springbootmall.dto.ProductRequest;
import org.example.springbootmall.model.Product;
import org.example.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {

        String sql="SELECT product_id,product_name,category,image_url,price,stock,description," +
                "created_date,last_modified_date FROM products WHERE 1=1";

        Map<String,Object> map = new HashMap<>();

        //category要用.name()，因ProductCategory
        if(productQueryParams.getCategory() != null){
            sql = sql + " and category = :category";
            map.put("category",productQueryParams.getCategory().name());
        }
        if(productQueryParams.getSearch() != null){
            sql = sql + " and product_name LIKE :search";
            map.put("search","%" + productQueryParams.getSearch() + "%");
        }

        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        return productList;
    }

    @Override
    public Product getByProductId(Integer productId) {

        String sql = "SELECT product_id,product_name,category,image_url,price,stock,description," +
                "created_date,last_modified_date FROM products" +
                " WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        String sql = "INSERT INTO products(product_name,category,image_url,price,stock,description,created_date,last_modified_date) " +
                "VALUES(:productName,:category,:imageUrl,:price,:stock,:description,:createdDate,:lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        String sql = "UPDATE products SET product_name = :productName,category = :category,image_url = :imageUrl, " +
                "price = :price,stock = :stock,description = :description, last_modified_date = :lastModifiedDate " +
                " WHERE product_id = :productId ";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getStock());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProduct(Integer productId) {

        String sql = "DELETE FROM products WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }
}
