package com.microservice.alimentos.alimentos.client.models;

import java.util.List;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
   private Long id;
   private String name;
   private String quantity;
   private Long code;
   private String imageUrl;
   private List<Object> tags;
   private List<Object> nutriments;
   private List<Object> ingredients;
   
}
