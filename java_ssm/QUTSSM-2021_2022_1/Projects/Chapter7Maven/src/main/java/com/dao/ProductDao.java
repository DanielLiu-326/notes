package com.dao;

import com.po.Product;
import org.springframework.stereotype.Repository;

@Repository("productDao")
public interface ProductDao {
    public Product selectProductById(Integer pid);
}
