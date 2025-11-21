package com.microservicio.dto;

public class MonopatinDTO {
  private Long id;
  private Long paradaId;
  private boolean activo;
  private double latitud;
  private double longitud;
  
  public MonopatinDTO() {
	
  }

  public Long getId() {
	return id;
  }

  public void setId(Long id) {
	this.id = id;
  }

  public Long getParadaId() {
	return paradaId;
  }

  public void setParadaId(Long paradaId) {
	this.paradaId = paradaId;
  }

  public boolean isActivo() {
	return activo;
  }

  public void setActivo(boolean activo) {
	this.activo = activo;
  }

  public double getLatitud() {
	return latitud;
  }

  public void setLatitud(double latitud) {
	this.latitud = latitud;
  }

  public double getLongitud() {
	return longitud;
  }

  public void setLongitud(double longitud) {
	this.longitud = longitud;
  }
  
  
  
}
