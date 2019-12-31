package com.ecommerce.microcommerce.services.Impl;

import com.ecommerce.microcommerce.Utile.UtileCommerce;
import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.model.ProductModel;
import com.ecommerce.microcommerce.services.ProductService;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductDao productDao ;

    public ProductServiceImpl (ProductDao productDao){
        this.productDao=productDao;
    }

    @Override
    public ProductModel findOne(int id) {
        Product produit = productDao.findById(id);
        if(produit==null)
            throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
        ProductModel model = UtileCommerce.entityToModel(produit);
        return model;
    }

    @Override
    public List<ProductModel> findAll() {
        List<Product> products = productDao.findAll();
        List<ProductModel> productModels = UtileCommerce.listeEntityToListeModel(products);
        return productModels;
    }

    @Override
    public ProductModel save(ProductModel model) {
        Product product = productDao.save(UtileCommerce.modelToEntity(model));
        return UtileCommerce.entityToModel(product);
    }
}
