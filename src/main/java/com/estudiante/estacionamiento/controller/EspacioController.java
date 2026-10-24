package com.estudiante.estacionamiento.controller;

import com.estudiante.estacionamiento.entity.Espacio;
import com.estudiante.estacionamiento.service.EspacioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/espacios")
public class EspacioController {

    private final EspacioService espacioService;

    public EspacioController(EspacioService espacioService) {
        this.espacioService = espacioService;
    }

    @GetMapping
    public List<Espacio> listarEspacios() {
        return espacioService.listarEspacios();
    }

    @PostMapping
    public Espacio guardarEspacio(@RequestBody Espacio espacio) {
        return espacioService.guardarEspacio(espacio);
    }
}