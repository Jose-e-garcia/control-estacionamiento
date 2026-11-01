package com.estudiante.estacionamiento.controller;

import com.estudiante.estacionamiento.entity.Espacio;
import com.estudiante.estacionamiento.service.EspacioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.estudiante.estacionamiento.dto.EspacioRequestDTO;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.DeleteMapping;

import com.estudiante.estacionamiento.dto.EspacioResponseDTO;



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
    public List<EspacioResponseDTO> listarEspacios() {
        return espacioService.listarEspacios();
    }

    @GetMapping("/{id}")
    public EspacioResponseDTO buscarEspacioPorId(@PathVariable Long id) {
        return espacioService.buscarEspacioPorIdRespuesta(id);
    }

    @GetMapping("/disponibles")
    public List<EspacioResponseDTO> listarEspaciosDisponibles() {
        return espacioService.listarEspaciosDisponibles();
    }

    @GetMapping("/disponibles/total")
    public long contarEspaciosDisponibles() {
        return espacioService.contarEspaciosDisponibles();
    }

    @PostMapping
    public EspacioResponseDTO guardarEspacio(@Valid @RequestBody EspacioRequestDTO datos) {
        return espacioService.guardarEspacio(datos);
    }

    @PutMapping("/{id}")
    public EspacioResponseDTO actualizarEspacio(
            @PathVariable Long id,
            @Valid @RequestBody EspacioRequestDTO datos) {
        return espacioService.actualizarEspacio(id, datos);
    }

    @DeleteMapping("/{id}")
    public void eliminarEspacio(@PathVariable Long id) {
        espacioService.eliminarEspacio(id);
    }

}