package com.microservice.alimentos.alimentos.client.models;


import java.io.Serializable;

import com.google.gson.JsonObject;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
   private String id;                      //id
    private String name;                    //product_name
    private String quantity;                //quantity
    private String imageUrl;                //image_front_url
    private Object nutriments;          //nutriments

   
}
