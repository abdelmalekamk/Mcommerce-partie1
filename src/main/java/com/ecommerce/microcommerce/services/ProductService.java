package com.ecommerce.microcommerce.services;

import com.ecommerce.microcommerce.model.ProductModel;

import java.util.List;

public interface ProductService {

    ProductModel findOne (int id);
    List<ProductModel> findAll();
    ProductModel save(ProductModel model);

}
