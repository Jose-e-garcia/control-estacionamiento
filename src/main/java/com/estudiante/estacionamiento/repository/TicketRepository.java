package com.estudiante.estacionamiento.repository;

import com.estudiante.estacionamiento.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}