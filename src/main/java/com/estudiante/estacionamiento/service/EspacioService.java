package com.estudiante.estacionamiento.service;

import com.estudiante.estacionamiento.entity.Espacio;
import com.estudiante.estacionamiento.repository.EspacioRepository;
import org.springframework.stereotype.Service;

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

    public Espacio guardarEspacio(Espacio espacio) {
        return espacioRepository.save(espacio);
    }
}