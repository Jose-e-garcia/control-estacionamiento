package com.estudiante.estacionamiento.dto;

import com.estudiante.estacionamiento.entity.Espacio;

public class EspacioResponseDTO {

    private Long id;
    private String codigo;
    private String tipo;
    private boolean ocupado;

    public EspacioResponseDTO(Long id, String codigo, String tipo, boolean ocupado) {
        this.id = id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.ocupado = ocupado;
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public static EspacioResponseDTO desdeEntidad(Espacio espacio) {
        return new EspacioResponseDTO(
                espacio.getId(),
                espacio.getCodigo(),
                espacio.getTipo(),
                espacio.isOcupado()
        );
    }


}