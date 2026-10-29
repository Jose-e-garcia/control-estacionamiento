package com.estudiante.estacionamiento.controller;

import com.estudiante.estacionamiento.entity.Espacio;
import com.estudiante.estacionamiento.service.EspacioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.estudiante.estacionamiento.dto.EspacioRequestDTO;

import jakarta.validation.Valid;

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

    @GetMapping("/disponibles")
    public List<Espacio> listarEspaciosDisponibles() {
        return espacioService.listarEspaciosDisponibles();
    }

    @GetMapping("/disponibles/total")
    public long contarEspaciosDisponibles() {
        return espacioService.contarEspaciosDisponibles();
    }

    @PostMapping
    public Espacio guardarEspacio(@Valid @RequestBody EspacioRequestDTO datos) {
        return espacioService.guardarEspacio(datos);
    }
}