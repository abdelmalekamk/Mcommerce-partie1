package com.ecommerce.microcommerce.Utile;

import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.model.ProductModel;

import java.util.List;
import java.util.stream.Collectors;

public class UtileCommerce {

    public static ProductModel entityToModel (Product product){
        ProductModel model = new ProductModel().toBuilder().build();
        model.setId(product.getId());
        model.setNom(product.getNom());
        model.setPrix(product.getPrix());
        model.setPrixAchat(product.getPrixAchat());
        return model;
    }

    public static Product modelToEntity (ProductModel model){
        Product product = new Product();
        product.setId(model.getId());
        product.setNom(model.getNom());
        product.setPrix(model.getPrix());
        product.setPrixAchat(model.getPrixAchat());
        return product;
    }

    public static List<ProductModel> listeEntityToListeModel(List<Product> products){
        return products.stream().map(p-> entityToModel(p)).collect(Collectors.toList());
    }

}
