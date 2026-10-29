package com.estudiante.estacionamiento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TicketRequestDTO {

    @NotBlank(message = "La placa es obligatoria")
    @Size(max = 10, message = "La placa no puede superar 10 caracteres")
    @Pattern(regexp = "[A-Za-z0-9 -]*", message = "La placa contiene caracteres no válidos")
    private String placa;

    @NotNull(message = "Debe seleccionar un espacio")
    @Positive(message = "El ID del espacio debe ser mayor que cero")
    private Long espacioId;

    @NotBlank(message = "El tipo de vehículo es obligatorio")
    @Pattern(regexp = "MOTOCICLETA|AUTO|PICKUP",
            message = "El tipo debe ser MOTOCICLETA, AUTO o PICKUP")
    private String tipoVehiculo;

    public TicketRequestDTO() {
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getEspacioId() {
        return espacioId;
    }

    public void setEspacioId(Long espacioId) {
        this.espacioId = espacioId;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }
}