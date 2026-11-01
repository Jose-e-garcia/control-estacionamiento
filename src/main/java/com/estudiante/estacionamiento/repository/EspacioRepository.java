package com.estudiante.estacionamiento.repository;

import com.estudiante.estacionamiento.entity.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface EspacioRepository extends JpaRepository<Espacio, Long> {

    List<Espacio> findByOcupadoFalse();

    long countByOcupadoFalse();

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdNot(String codigo, Long id);

}