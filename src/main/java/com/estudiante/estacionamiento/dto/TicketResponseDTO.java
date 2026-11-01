package com.estudiante.estacionamiento.dto;

import com.estudiante.estacionamiento.entity.Ticket;
import java.time.LocalDateTime;

public class TicketResponseDTO {

    private Long id;
    private String placa;
    private String tipoVehiculo;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private String estado;
    private Double total;
    private Long espacioId;
    private String codigoEspacio;

    public TicketResponseDTO(
            Long id,
            String placa,
            String tipoVehiculo,
            LocalDateTime horaEntrada,
            LocalDateTime horaSalida,
            String estado,
            Double total,
            Long espacioId,
            String codigoEspacio) {

        this.id = id;
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.estado = estado;
        this.total = total;
        this.espacioId = espacioId;
        this.codigoEspacio = codigoEspacio;
    }

    public Long getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public Double getTotal() {
        return total;
    }

    public Long getEspacioId() {
        return espacioId;
    }

    public String getCodigoEspacio() {
        return codigoEspacio;
    }

    public static TicketResponseDTO desdeEntidad(Ticket ticket) {
        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getPlaca(),
                ticket.getTipoVehiculo(),
                ticket.getHoraEntrada(),
                ticket.getHoraSalida(),
                ticket.getEstado(),
                ticket.getTotal(),
                ticket.getEspacio().getId(),
                ticket.getEspacio().getCodigo()
        );
    }
}