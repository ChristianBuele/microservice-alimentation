package com.microservice.alimentos.alimentos.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.alimentos.alimentos.client.models.User;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name = "user-service",url = "http://54.160.229.191:8081" )
public interface UserClient {
    
    
    @CircuitBreaker(name = "userCB",fallbackMethod = "getUserFallback")
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Integer id);

    default ResponseEntity<User> getUserFallback(RuntimeException e){
        User user = User.builder().firstName("No disponible").build();
    return ResponseEntity.ok(user);
    }
}
