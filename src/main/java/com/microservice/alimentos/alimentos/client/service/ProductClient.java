package com.microservice.alimentos.alimentos.client.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.alimentos.alimentos.client.models.Product;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name = "products-service", url="https://productosmicroapi.herokuapp.com/")
public interface ProductClient {

    @CircuitBreaker(name = "productCB",fallbackMethod = "getProductFallback")
    
    @GetMapping("api/{name}")
    public ResponseEntity<List<Product>> getProduct(@PathVariable("name") String name);


    default ResponseEntity<List<Product>> getProductFallback(RuntimeException e) {
        List<Product> products=new ArrayList<>();
        products.add(Product.builder().build());
        return ResponseEntity.ok(products);
    }
}
