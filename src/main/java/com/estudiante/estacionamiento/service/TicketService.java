package com.estudiante.estacionamiento.service;

import com.estudiante.estacionamiento.entity.Espacio;
import com.estudiante.estacionamiento.entity.Ticket;
import com.estudiante.estacionamiento.repository.EspacioRepository;
import com.estudiante.estacionamiento.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EspacioRepository espacioRepository;

    public TicketService(TicketRepository ticketRepository, EspacioRepository espacioRepository) {
        this.ticketRepository = ticketRepository;
        this.espacioRepository = espacioRepository;
    }

    public Ticket guardarTicket(Ticket ticket) {
        Long espacioId = ticket.getEspacio().getId();

        Espacio espacio = espacioRepository.findById(espacioId)
                .orElseThrow(() -> new RuntimeException("El espacio no existe"));

        ticket.setEspacio(espacio);

        return ticketRepository.save(ticket);
    }
}