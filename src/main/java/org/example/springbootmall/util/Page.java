package org.example.springbootmall.util;

import org.example.springbootmall.model.Product;

import java.util.List;

public class Page<T>{

    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<Product> result;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Product> getResult() {
        return result;
    }

    public void setResult(List<Product> result) {
        this.result = result;
    }
}
