package com.estudiante.estacionamiento.service;

import com.estudiante.estacionamiento.dto.TicketRequestDTO;
import com.estudiante.estacionamiento.entity.Espacio;
import com.estudiante.estacionamiento.entity.Ticket;
import com.estudiante.estacionamiento.exception.RecursoNoEncontradoException;
import com.estudiante.estacionamiento.repository.EspacioRepository;
import com.estudiante.estacionamiento.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EspacioRepository espacioRepository;

    public TicketService(TicketRepository ticketRepository, EspacioRepository espacioRepository) {
        this.ticketRepository = ticketRepository;
        this.espacioRepository = espacioRepository;
    }

    public List<Ticket> listar() {
        return ticketRepository.findAll();
    }

    public Ticket buscarPorId(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un ticket con el ID " + id));
    }

    @Transactional
    public Ticket registrar(TicketRequestDTO datos) {
        Espacio espacio = espacioRepository.findById(datos.getEspacioId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un espacio con el ID " + datos.getEspacioId()));

        if (espacio.isOcupado()) {
            throw new IllegalStateException("El espacio ya está ocupado");
        }

        Ticket ticket = new Ticket();
        ticket.setPlaca(datos.getPlaca());
        ticket.setHoraEntrada(LocalDateTime.now());
        ticket.setEstado("ACTIVO");
        ticket.setEspacio(espacio);

        espacio.setOcupado(true);
        espacioRepository.save(espacio);

        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket actualizar(Long id, TicketRequestDTO datos) {
        Ticket ticket = buscarPorId(id);

        Espacio nuevoEspacio = espacioRepository.findById(datos.getEspacioId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un espacio con el ID " + datos.getEspacioId()));

        Espacio espacioAnterior = ticket.getEspacio();

        if (!espacioAnterior.getId().equals(nuevoEspacio.getId())) {
            if (nuevoEspacio.isOcupado()) {
                throw new IllegalStateException("El espacio ya está ocupado");
            }

            espacioAnterior.setOcupado(false);
            nuevoEspacio.setOcupado(true);

            espacioRepository.save(espacioAnterior);
            espacioRepository.save(nuevoEspacio);

            ticket.setEspacio(nuevoEspacio);
        }

        ticket.setPlaca(datos.getPlaca());

        return ticketRepository.save(ticket);
    }

    @Transactional
    public void eliminar(Long id) {
        Ticket ticket = buscarPorId(id);

        if ("ACTIVO".equals(ticket.getEstado())) {
            Espacio espacio = ticket.getEspacio();
            espacio.setOcupado(false);
            espacioRepository.save(espacio);
        }

        ticketRepository.delete(ticket);
    }
}