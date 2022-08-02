package com.microservice.alimentos.alimentos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<Alimentation> findAllAlimentations(Integer page) {
        Pageable paging = PageRequest.of(
            page, 5, Sort.by("createAt").descending());
        Page<Alimentation> alimentations = alimentationRespository.findAll(paging);
        List<Alimentation> alimentationsNew=alimentations.getContent().stream().map(alimentation->{
            User user=userClient.getUser(alimentation.getUserId()).getBody();
            alimentation.setUser(user);
            List<Product> s =productClient.getProduct(alimentation.getName()).getBody();
            alimentation.setProductos(s);
            return alimentation;
        }).collect(Collectors.toList());
       

        return alimentationsNew;
    }

    @Override
    public Alimentation getAlimentationById(Integer id) {
        // TODO Auto-generated method
        Alimentation alimentation = alimentationRespository.findById(id).orElse(null);
        if (alimentation != null) {
            User user=userClient.getUser(alimentation.getUserId()).getBody();
            alimentation.setUser(user);
            List<Product> s =productClient.getProduct(alimentation.getName()).getBody();
            alimentation.setProductos(s);
        }
        return alimentation;
    }

    
}
