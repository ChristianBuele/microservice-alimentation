package com.microservice.alimentos.alimentos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.microservice.alimentos.alimentos.client.models.Product;
import com.microservice.alimentos.alimentos.client.models.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="tb_alimentation")
public class Alimentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Valid
    @Column(name = "comida")
    @Positive(message = "La comida debe ser un numero positivo")
    @NonNull
    @Min(value = 1, message = "El minimo es 1")
    @Max(value = 3,message = "El máximo es 3")
    Integer comida;


    @Valid
    @Column(name = "porcion")
    @Positive(message = "La porcion debe ser un numero positivo")
    Double porcion;

    @Transient
    Product producto;

    @Transient
    User user;
}
