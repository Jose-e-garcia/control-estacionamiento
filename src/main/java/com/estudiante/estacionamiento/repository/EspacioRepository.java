package com.estudiante.estacionamiento.repository;

import com.estudiante.estacionamiento.entity.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspacioRepository extends JpaRepository<Espacio, Long> {
}