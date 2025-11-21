package com.microservicio.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "mapa")
public class Mapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Guarda solo ids de paradas
    @ElementCollection
    private List<Long> paradasId = new ArrayList<>();

    public Long getId() { return id; }
    public List<Long> getParadasId() { return paradasId; }
    public void setParadasId(List<Long> paradasId) { this.paradasId = paradasId; }
}


