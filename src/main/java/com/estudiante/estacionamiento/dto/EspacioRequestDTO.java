package com.estudiante.estacionamiento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EspacioRequestDTO {

    @NotBlank(message = "El código del espacio es obligatorio")
    @Size(max = 10, message = "El código no puede superar 10 caracteres")
    @Pattern(regexp = "[A-Za-z0-9-]+", message = "El código solo puede contener letras, números y guiones")
    private String codigo;

    @NotBlank(message = "El tipo de espacio es obligatorio")
    @Pattern(regexp = "MOTOCICLETA|AUTO|PICKUP", message = "El tipo debe ser MOTOCICLETA, AUTO o PICKUP")
    private String tipo;

    public EspacioRequestDTO() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}