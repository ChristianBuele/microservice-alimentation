package com.microservice.alimentos.alimentos.client.service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.alimentos.alimentos.client.models.Product;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name = "products-service")
public interface ProductClient {

    @CircuitBreaker(name = "productCB",fallbackMethod = "getProductFallback")
    
    @GetMapping("/api/{name}")
    public ResponseEntity<Product> getProduct(@PathVariable("name") String name);


    default ResponseEntity<Product> getProductFallback(RuntimeException e) {
        Product product = Product.builder().name("No disponible").build();
        return ResponseEntity.ok(product);
    }
}
