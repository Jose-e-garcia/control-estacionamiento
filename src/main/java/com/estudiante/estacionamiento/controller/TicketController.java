package com.estudiante.estacionamiento.controller;

import com.estudiante.estacionamiento.entity.Ticket;
import com.estudiante.estacionamiento.service.TicketService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public Ticket guardarTicket(@RequestBody Ticket ticket) {
        return ticketService.guardarTicket(ticket);
    }
}