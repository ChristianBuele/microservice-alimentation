package com.microservice.alimentos.alimentos.service;

import java.util.List;

import com.microservice.alimentos.alimentos.entity.Alimentation;

public interface AlimentationService {
    
    public List<Alimentation> findAllAlimentations(Integer page);
    public Alimentation getAlimentationById(Integer id);
    
}
