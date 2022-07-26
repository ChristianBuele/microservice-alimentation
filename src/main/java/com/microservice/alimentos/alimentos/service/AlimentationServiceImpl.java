package com.microservice.alimentos.alimentos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.alimentos.alimentos.client.models.Product;
import com.microservice.alimentos.alimentos.client.models.User;
import com.microservice.alimentos.alimentos.client.service.ProductClient;
import com.microservice.alimentos.alimentos.client.service.UserClient;
import com.microservice.alimentos.alimentos.entity.Alimentation;
import com.microservice.alimentos.alimentos.repository.AlimentationRespository;

@Service
public class AlimentationServiceImpl implements AlimentationService {

    @Autowired
    AlimentationRespository alimentationRespository;

    @Autowired
    ProductClient productClient;

    @Autowired
    UserClient userClient;

    @Override
    public List<Alimentation> findAllAlimentations() {
        List<Alimentation> alimentations = alimentationRespository.findAll();
        List<Alimentation> alimentationsNew=alimentations.stream().map(alimentation->{
            // List<Product> s = productClient.getProduct(alimentation.getName()).getBody();
            // System.out.println("llega del hneryyyyyyyyyyy \n\n\n\n");
            // System.out.println(s);
            // alimentation.setProductos(s);
            return alimentation;
        }).collect(Collectors.toList());
       

        return alimentationsNew;
    }

    @Override
    public Alimentation getAlimentationById(Integer id) {
        // TODO Auto-generated method
        Alimentation alimentation = alimentationRespository.findById(id).orElse(null);
        if (alimentation != null) {
            User user=userClient.getUser(2).getBody();
            alimentation.setUser(user);
            List<Product> s =productClient.getProduct(alimentation.getName()).getBody();
            alimentation.setProductos(s);
    
        }
        return alimentation;
    }

    
}
