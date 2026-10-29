package com.estudiante.estacionamiento.repository;

import com.estudiante.estacionamiento.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByEstado(String estado);

    long countByEstado(String estado);

    long countByEstadoAndEliminadoFalse(String estado);

    List<Ticket> findByEliminadoFalse();

    List<Ticket> findByEstadoAndEliminadoFalse(String estado);

    @Query("SELECT COALESCE(SUM(t.total), 0) FROM Ticket t WHERE t.estado = 'FINALIZADO' AND t.eliminado = false AND t.horaSalida >= :inicio AND t.horaSalida < :fin")
    Double calcularTotalRecaudado(LocalDateTime inicio, LocalDateTime fin);


}