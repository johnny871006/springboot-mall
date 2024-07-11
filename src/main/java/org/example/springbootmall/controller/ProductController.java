package org.example.springbootmall.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.example.springbootmall.constant.ProductCategory;
import org.example.springbootmall.dto.ProductQueryParams;
import org.example.springbootmall.dto.ProductRequest;
import org.example.springbootmall.model.Product;
import org.example.springbootmall.service.ProductService;
import org.example.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(

          // 查詢條件
          @RequestParam(required = false)  ProductCategory category,
          @RequestParam(required = false) String search,
          // 查詢排序
          @RequestParam(defaultValue = "created_date") String orderBy,
          @RequestParam(defaultValue = "desc") String sort,
          //查詢分頁
          @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
          @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ){
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        // 取得商品清單
        List<Product> productList = productService.getProducts(productQueryParams);

        // 取得商品總數
        Integer total = productService.getCount(productQueryParams);

        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResult(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }


    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){

        Product product = productService.getByProductId(productId);

        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){

        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getByProductId(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody ProductRequest productRequest){

        Product product = productService.getByProductId(productId);

        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productService.updateProduct(productId,productRequest);

        Product updateProduct = productService.getByProductId(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){

        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
