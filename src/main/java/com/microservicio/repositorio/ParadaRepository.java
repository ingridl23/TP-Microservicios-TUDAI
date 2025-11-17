package com.microservicio.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.modelo.Monopatin;
import com.microservicio.modelo.Parada;

public interface ParadaRepository extends JpaRepository<Parada, Long> {
    public List<Monopatin>getMonopatinesAll();
}