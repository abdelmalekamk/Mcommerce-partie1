package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.model.ProductModel;
import com.ecommerce.microcommerce.services.ProductService;
import com.ecommerce.microcommerce.web.exceptions.PrixIncorrectException;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Api( description="API pour es opérations CRUD sur les produits.")

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;


    //Récupérer la liste des produits

    @RequestMapping(value = "/Produits", method = RequestMethod.GET)

    public List<ProductModel> listeProduits() {

//        Iterable<Product> produits = productDao.findAll();
//
//        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
//
//        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
//
//        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
//
//        produitsFiltres.setFilters(listDeNosFiltres);

        List<ProductModel> productModels = productService.findAll();

        return productModels;
    }


    //Récupérer un produit par son Id
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @GetMapping(value = "/Produits/{id}")

    public ProductModel afficherUnProduit(@PathVariable int id) {
        ProductModel model = productService.findOne(id);
        return model;
    }




    //ajouter un produit
    @PostMapping(value = "/Produits")

    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody ProductModel product) throws PrixIncorrectException {

        if (product.getPrix() <= 0) throw new PrixIncorrectException("le prix ne doit pas etre inferieur à zéro ") ;

        ProductModel created = productService.save(product);

        if (created == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping (value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {

        productDao.delete(id);
    }

    @PutMapping (value = "/Produits")
    public void updateProduit(@RequestBody Product product) {

        productDao.save(product);
    }

    @GetMapping(value = "/AdminProduits")
    public List<Product> calculerMargeProduit( ){
        List<Product> product = productDao.findAll();
        List marge = new ArrayList();
        for (Product product1:product) {
            marge.add(product1.toString()+":"+Integer.toString(product1.getPrix()-product1.getPrixAchat()));
        }
        return marge;
    }

    @GetMapping(value = "/ListProduits")
    public List<Product> trierProduitsParOrdreAlphabetique(){
//        List<Product> produit =productDao.listeProduitOrOrderByNom();
//        return produit;
        return productDao.findAllByOrderByNom();
    }

    //Pour les tests
    @GetMapping(value = "test/produits/{prix}")
    public List<Product>  testeDeRequetes(@PathVariable int prix) {

        return productDao.chercherUnProduitCher(400);
    }



}
