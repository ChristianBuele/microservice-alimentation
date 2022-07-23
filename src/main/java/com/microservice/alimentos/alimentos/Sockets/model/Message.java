package com.microservice.alimentos.alimentos.Sockets.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Message {

    private String from;
    private String text;

    

}