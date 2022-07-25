package com.microservice.alimentos.alimentos.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

    @Column(name = "name")
    @NonNull
    private String name;


    @Valid
    @Column(name = "porcion")
    @Positive(message = "La porcion debe ser un numero positivo")
    Double porcion;

    @Positive(message = "Es necesario el id usuario")
    @Column(name = "user_id")
    private Integer userId;

    @Transient
    List<Product> productos;

    @Transient
    User user;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }
}
