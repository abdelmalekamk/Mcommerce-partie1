package com.ecommerce.microcommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {

    private int id;
    private String nom;
    private int prix;
    private int prixAchat ;

}
