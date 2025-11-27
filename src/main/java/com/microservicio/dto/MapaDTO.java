package com.microservicio.dto;

import java.util.List;

public class MapaDTO {
    private Long id;
    private List<Long> paradasId;
    private String nombre;

    
    public MapaDTO() {
	}

	public MapaDTO(Long id, List<Long> paradasId, String nombre) {
    	
    	this.id = id;
    	this.paradasId = paradasId;
    	this.nombre = nombre;
    }
    
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getParadasId() {
		return paradasId;
	}

	public void setParadasId(List<Long> paradasId) {
		this.paradasId = paradasId;
	}

	public String getNombre() { 
		return nombre; 
		}
	
    public void setNombre(String nombre) { 
    	this.nombre = nombre; 
    	}
    
}

