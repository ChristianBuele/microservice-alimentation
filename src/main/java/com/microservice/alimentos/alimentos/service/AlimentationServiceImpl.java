package com.microservice.alimentos.alimentos.service;

import java.util.List;

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
    public List<Alimentation> findAllInvoice() {
        List<Alimentation> alimentations = alimentationRespository.findAll();


        return alimentations;
    }

    @Override
    public Alimentation getAlimentationById(Integer id) {
        // TODO Auto-generated method stub
        Alimentation alimentation = alimentationRespository.findById(id).orElse(null);
        if (alimentation != null) {
            User user=userClient.getUser(2).getBody();
            alimentation.setUser(user);
            Product product=productClient.getProduct(alimentation.getName()).getBody();
            alimentation.setProducto(product);
        }
        return alimentation;
    }

    
}
