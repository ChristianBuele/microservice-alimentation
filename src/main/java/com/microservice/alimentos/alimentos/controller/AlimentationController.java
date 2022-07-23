package com.microservice.alimentos.alimentos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.alimentos.alimentos.Sockets.model.Message;
import com.microservice.alimentos.alimentos.entity.Alimentation;
import com.microservice.alimentos.alimentos.repository.AlimentationRespository;

@Controller
public class AlimentationController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private AlimentationRespository alimentationRespository;

    
    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/alimentation")
    public ResponseEntity<List<Alimentation>> getAlimentation() {
        List<Alimentation> alimentations = alimentationRespository.findAll();
        return new ResponseEntity<List<Alimentation>>(alimentations, HttpStatus.OK);
    }

    @PostMapping("/alimentation")
    public ResponseEntity<Alimentation> postAlimentation(@Valid @RequestBody Alimentation alimentation,BindingResult result) {
        System.out.println("Llega nuevo alimento");
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Alimentation newAlimentation= this.alimentationRespository.save(alimentation);
        if(newAlimentation!=null){
            this.sendNotification(newAlimentation);
            return new ResponseEntity<Alimentation>(alimentation, HttpStatus.OK);
        }
        return new ResponseEntity<Alimentation>(HttpStatus.BAD_REQUEST);
        
    }

    public void sendNotification(Alimentation alimentation) {
        System.out.println("Fire");
        this.template.convertAndSend("/topic/greetings",alimentation);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("El error es:"+jsonString);
        return jsonString;
    }
}
