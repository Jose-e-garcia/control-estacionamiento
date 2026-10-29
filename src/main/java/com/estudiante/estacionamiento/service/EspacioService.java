package com.estudiante.estacionamiento.service;

import com.estudiante.estacionamiento.entity.Espacio;
import com.estudiante.estacionamiento.repository.EspacioRepository;
import org.springframework.stereotype.Service;

import com.estudiante.estacionamiento.dto.EspacioRequestDTO;

import java.util.List;

@Service
public class EspacioService {

    private final EspacioRepository espacioRepository;

    public EspacioService(EspacioRepository espacioRepository) {
        this.espacioRepository = espacioRepository;
    }

    public List<Espacio> listarEspacios() {
        return espacioRepository.findAll();
    }

    public List<Espacio> listarEspaciosDisponibles() {
        return espacioRepository.findByOcupadoFalse();
    }

    public long contarEspaciosDisponibles() {
        return espacioRepository.countByOcupadoFalse();
    }

    public Espacio guardarEspacio(EspacioRequestDTO datos) {
        Espacio espacio = new Espacio();
        espacio.setCodigo(datos.getCodigo());
        espacio.setTipo(datos.getTipo());
        espacio.setOcupado(false);

        return espacioRepository.save(espacio);
    }
}