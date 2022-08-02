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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.alimentos.alimentos.client.models.Product;
import com.microservice.alimentos.alimentos.client.service.ProductClient;
import com.microservice.alimentos.alimentos.entity.Alimentation;
import com.microservice.alimentos.alimentos.repository.AlimentationRespository;
import com.microservice.alimentos.alimentos.service.AlimentationServiceImpl;

@Controller
public class AlimentationController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private AlimentationServiceImpl alimentationService;

    @Autowired
    private AlimentationRespository alimentationRespository;

    @Autowired
    private ProductClient productClient;
    
    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/alimentation")
    public ResponseEntity<List<Alimentation>> getAlimentation(@RequestParam(name = "page", defaultValue = "0") int page) {
        List<Alimentation> alimentations = alimentationService.findAllAlimentations(page);

        return new ResponseEntity<List<Alimentation>>(alimentations, HttpStatus.OK);
    }

    @GetMapping("/alimentation/user/{id}")
    public ResponseEntity<List<Alimentation>> getAlimentationByUser(@PathVariable("id") Integer id) {
        List<Alimentation> alimentations = alimentationRespository.findByUserId(id);
        return new ResponseEntity<List<Alimentation>>(alimentations, HttpStatus.OK);
    }

    @PostMapping("/alimentation")
    public ResponseEntity<Alimentation> postAlimentation(@Valid @RequestBody Alimentation alimentation,BindingResult result) {
        System.out.println("Llega nuevo alimento");
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        List<Product> newProducts=productClient.getProduct(alimentation.getName()).getBody();
        // System.out.println("Nuevos productos: "+newProducts);
        if(newProducts.size()==0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontraron productos con el nombre "+alimentation.getName());
        }
        Alimentation newAlimentation= this.alimentationRespository.save(alimentation);
        if(newAlimentation!=null){
            newAlimentation.setProductos(newProducts);
            this.sendNotification(newAlimentation);
            return new ResponseEntity<Alimentation>(alimentation, HttpStatus.OK);
        }
        return new ResponseEntity<Alimentation>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/alimentation/verify")
    public ResponseEntity<Object> verifyAlimentation(@RequestParam (name = "comida") Integer comida,@RequestParam(name="usuario") Integer IdUsuario) {
        System.out.println("Verificando alimento");
        return ResponseEntity.ok("");
    }
    @GetMapping("/alimentation/{id}")
    public ResponseEntity<Alimentation> getAlimentationById(@PathVariable Integer id) {

        Alimentation newAlimentation= this.alimentationService.getAlimentationById(id);
        if(newAlimentation!=null){
            return new ResponseEntity<Alimentation>(newAlimentation, HttpStatus.OK);
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
