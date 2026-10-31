package com.estudiante.estacionamiento.service;

import com.estudiante.estacionamiento.dto.TicketRequestDTO;
import com.estudiante.estacionamiento.entity.Espacio;
import com.estudiante.estacionamiento.entity.Ticket;
import com.estudiante.estacionamiento.exception.RecursoNoEncontradoException;
import com.estudiante.estacionamiento.repository.EspacioRepository;
import com.estudiante.estacionamiento.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
        return ticketRepository.findByEliminadoFalse();
    }

    public List<Ticket> listarPorEstado(String estado) {
        return ticketRepository.findByEstadoAndEliminadoFalse(estado);
    }

    public long contarTicketsPorEstado(String estado) {
        return ticketRepository.countByEstadoAndEliminadoFalse(estado);
    }

    public Double calcularTotalRecaudadoHoy() {
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);

        return ticketRepository.calcularTotalRecaudado(inicio, fin);
    }

    public Ticket buscarPorId(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un ticket con el ID " + id));

        if (ticket.isEliminado()) {
            throw new RecursoNoEncontradoException(
                    "No existe un ticket con el ID " + id);
        }

        return ticket;
    }

    @Transactional
    public Ticket registrar(TicketRequestDTO datos) {
        Espacio espacio = espacioRepository.findById(datos.getEspacioId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un espacio con el ID " + datos.getEspacioId()));

        if (espacio.isOcupado()) {
            throw new IllegalStateException("El espacio ya está ocupado");
        }

        if (!espacio.getTipo().equals(datos.getTipoVehiculo())) {
            throw new IllegalStateException("El tipo de vehículo no coincide con el tipo de espacio");
        }

        Ticket ticket = new Ticket();
        ticket.setPlaca(datos.getPlaca());

        ticket.setTipoVehiculo(datos.getTipoVehiculo());

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

        if (ticket.isEliminado()) {
            throw new RecursoNoEncontradoException("No existe un ticket con el ID " + id);
        }

        if ("FINALIZADO".equals(ticket.getEstado())) {
            throw new IllegalStateException("No se puede modificar un ticket finalizado");
        }

        Espacio nuevoEspacio = espacioRepository.findById(datos.getEspacioId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un espacio con el ID " + datos.getEspacioId()));


        if (!nuevoEspacio.getTipo().equals(datos.getTipoVehiculo())) {
            throw new IllegalStateException("El tipo de vehículo no coincide con el tipo de espacio");
        }


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

        ticket.setTipoVehiculo(datos.getTipoVehiculo());

        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket finalizar(Long id) {
        Ticket ticket = buscarPorId(id);

        if (!"ACTIVO".equals(ticket.getEstado())) {
            throw new IllegalStateException("El ticket ya está finalizado");
        }

        LocalDateTime horaSalida = LocalDateTime.now();
        ticket.setHoraSalida(horaSalida);

        long minutos = java.time.Duration.between(
                ticket.getHoraEntrada(), horaSalida).toMinutes();

        long horas = (minutos + 59) / 60;

        if (horas < 1) {
            horas = 1;
        }

        double tarifa;

        switch (ticket.getTipoVehiculo()) {
            case "MOTOCICLETA":
                tarifa = 5.0;
                break;
            case "AUTO":
                tarifa = 8.0;
                break;
            case "PICKUP":
                tarifa = 12.0;
                break;
            default:
                throw new IllegalStateException("Tipo de vehículo no válido");
        }

        ticket.setTotal(horas * tarifa);
        ticket.setEstado("FINALIZADO");

        Espacio espacio = ticket.getEspacio();
        espacio.setOcupado(false);
        espacioRepository.save(espacio);

        return ticketRepository.save(ticket);
    }

    @Transactional
    public void eliminar(Long id) {
        Ticket ticket = buscarPorId(id);



        if ("ACTIVO".equals(ticket.getEstado())) {
            throw new IllegalStateException("No se puede eliminar un ticket activo");
        }

        ticket.setEliminado(true);
        ticketRepository.save(ticket);
    }
}