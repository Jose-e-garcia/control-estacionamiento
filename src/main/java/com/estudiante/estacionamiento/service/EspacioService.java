package com.estudiante.estacionamiento.service;

import com.estudiante.estacionamiento.entity.Espacio;
import com.estudiante.estacionamiento.repository.EspacioRepository;
import org.springframework.stereotype.Service;

import com.estudiante.estacionamiento.dto.EspacioRequestDTO;

import com.estudiante.estacionamiento.exception.RecursoNoEncontradoException;

import com.estudiante.estacionamiento.repository.TicketRepository;

import com.estudiante.estacionamiento.dto.EspacioResponseDTO;

import java.util.List;

@Service
public class EspacioService {

    private final EspacioRepository espacioRepository;
    private final TicketRepository ticketRepository;

    public EspacioService(EspacioRepository espacioRepository, TicketRepository ticketRepository) {
        this.espacioRepository = espacioRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<EspacioResponseDTO> listarEspacios() {
        return espacioRepository.findAll()
                .stream()
                .map(EspacioResponseDTO::desdeEntidad)
                .toList();
    }

    public Espacio buscarEspacioPorId(Long id) {
        return espacioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un espacio con el ID " + id));
    }

    public EspacioResponseDTO buscarEspacioPorIdRespuesta(Long id) {
        Espacio espacio = buscarEspacioPorId(id);
        return EspacioResponseDTO.desdeEntidad(espacio);
    }


    public List<EspacioResponseDTO> listarEspaciosDisponibles() {
        return espacioRepository.findByOcupadoFalse()
                .stream()
                .map(EspacioResponseDTO::desdeEntidad)
                .toList();
    }

    public long contarEspaciosDisponibles() {
        return espacioRepository.countByOcupadoFalse();
    }

    public EspacioResponseDTO guardarEspacio(EspacioRequestDTO datos) {

        if (espacioRepository.existsByCodigo(datos.getCodigo())) {
            throw new IllegalStateException("Ya existe un espacio con ese código");
        }

        Espacio espacio = new Espacio();
        espacio.setCodigo(datos.getCodigo());
        espacio.setTipo(datos.getTipo());
        espacio.setOcupado(false);

        return EspacioResponseDTO.desdeEntidad(espacioRepository.save(espacio));
    }

    public EspacioResponseDTO actualizarEspacio(Long id, EspacioRequestDTO datos) {

        Espacio espacio = buscarEspacioPorId(id);

        if (espacio.isOcupado()) {
            throw new IllegalStateException("No se puede modificar un espacio ocupado");
        }

        if (espacioRepository.existsByCodigoAndIdNot(datos.getCodigo(), id)) {
            throw new IllegalStateException("Ya existe otro espacio con ese código");
        }

        espacio.setCodigo(datos.getCodigo());
        espacio.setTipo(datos.getTipo());

        return EspacioResponseDTO.desdeEntidad(espacioRepository.save(espacio));
    }


    public void eliminarEspacio(Long id) {

        Espacio espacio = buscarEspacioPorId(id);

        if (espacio.isOcupado()) {
            throw new IllegalStateException("No se puede eliminar un espacio ocupado");
        }

        if (ticketRepository.existsByEspacio_Id(id)) {
            throw new IllegalStateException("No se puede eliminar un espacio que tiene tickets asociados");
        }

        espacioRepository.delete(espacio);
    }


}