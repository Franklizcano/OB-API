package com.example.obapi1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${environment.name}")
    private String environment;

    @GetMapping("/saludo")
    public String saludo() {
       return "Hola mundo! Estás en el ambiente de " + environment;
    }
}
