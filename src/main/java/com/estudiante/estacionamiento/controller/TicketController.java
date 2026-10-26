package com.estudiante.estacionamiento.controller;

import com.estudiante.estacionamiento.dto.TicketRequestDTO;
import com.estudiante.estacionamiento.entity.Ticket;
import com.estudiante.estacionamiento.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<Ticket> listar() {
        return ticketService.listar();
    }

    @GetMapping("/{id}")
    public Ticket buscarPorId(@PathVariable Long id) {
        return ticketService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Ticket> registrar(@Valid @RequestBody TicketRequestDTO datos) {
        Ticket ticket = ticketService.registrar(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }

    @PutMapping("/{id}")
    public Ticket actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TicketRequestDTO datos) {
        return ticketService.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ticketService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}