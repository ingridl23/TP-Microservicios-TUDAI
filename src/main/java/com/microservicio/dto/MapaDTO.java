package com.microservicio.dto;

import java.util.List;

public class MapaDTO {
    private Long id;
    private List<Long> paradasId;
    
    
    
    public MapaDTO() {
		super();
	}



	public MapaDTO(Long id, List<Long> paradasId) {
    	
    	this.id = id;
    	this.paradasId = paradasId;
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




    
    
    
}

