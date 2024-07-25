package com.api.CoolGlasses.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/Api")
public class ControllersPrueba {

    @GetMapping
    public ResponseEntity<String> GetPruebas() {
        return ResponseEntity.ok("Esto es una prueba de Api rest con Spring Boot!");
    }
}


