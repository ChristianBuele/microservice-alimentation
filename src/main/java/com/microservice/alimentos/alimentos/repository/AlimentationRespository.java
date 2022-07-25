package com.microservice.alimentos.alimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.alimentos.alimentos.entity.Alimentation;

public interface AlimentationRespository extends JpaRepository<Alimentation,Integer> {
}
