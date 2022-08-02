package com.microservice.alimentos.alimentos.client.models;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String sex;
    private String age;
    private String state;
}
