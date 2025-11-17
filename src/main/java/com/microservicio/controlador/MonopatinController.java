package com.microservicio.controlador;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.modelo.Monopatin;
import com.microservicio.servicio.MonopatinService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/monopatines")
public class MonopatinController {

    private final MonopatinService service;

    public MonopatinController(MonopatinService service) {
        this.service = service;
    }

    @Operation(summary="Obtiene todos los monopatines")
    @GetMapping
    public List<Monopatin> getAll() {
        return service.findAll();
    }

    @Operation(summary="Obtiene el monopatin con la id")
    @GetMapping("/{id}")
    public Monopatin getById(
    		@Parameter(description = "ID del monopatin a buscar") 
    		@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary="Crea un monopatin")
    @PostMapping
    public ResponseEntity<Monopatin> create(@RequestBody Monopatin m) {
        Monopatin created = service.create(m);
        return ResponseEntity.created(URI.create("/monopatines/" + created.getId())).body(created);
    }

    @Operation(summary="Actualiza los datos de un monopatin")
    @PutMapping("/{id}")
    public Monopatin update(
    		@Parameter(description = "ID del monopatin a actualizar") 
    		@PathVariable Long id, @RequestBody Monopatin m) {
        return service.update(id, m);
    }

    @Operation(summary="Borra el monopatin con la id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
    		@Parameter(description = "ID del monopatin a eliminar") 
    		@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary="Comienza el mantenimiento de un monopatin con la id")
    @PutMapping("/{id}/mantenimiento/inicio")
    public Monopatin comenzar(
    		@Parameter(description = "ID del monopatin en mantenimiento") 
    		@PathVariable Long id) {
        return service.comenzarMantenimiento(id);
    }

    @Operation(summary="Finaliza el mantenimiento de un monopatin con la id")
    @PutMapping("/{id}/mantenimiento/fin")
    public Monopatin terminar(
    		@Parameter(description = "ID del monopatin en mantenimiento") 
    		@PathVariable Long id) {
        return service.terminarMantenimiento(id);
    }
    
    @Operation(summary="Cambia la ubicación del monopatin (int Latitud, int longitud)")
    @PatchMapping("/{id}/localizacion")
    public Monopatin desplazarMonopatin(
    		@Parameter(description = "ID del monopatin") 
    		@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        return service.cambiarUbicacion(id, fields);
    }
    
    @Operation(summary="Inicia la pausa de un monopatin")
    @PatchMapping("/{id}/pausa")
    public Monopatin pausa(
    		@Parameter(description = "ID del monopatin a pausar") 
    		@PathVariable Long id) {
        return service.pausarMonopatin(id);
    }
    
    @Operation(summary="Suma kilometros en un monopatin")
    @PatchMapping("/{id}/sumarKilometros")
    public Monopatin sumarkm(
    		@Parameter(description = "ID del monopatin") 
    		@PathVariable Long id, 
    		@Parameter(description = "kilometros a sumar (double)") 
    		@RequestParam double km) {
        return service.sumarkm(id,km);
    }
    
    @Operation(summary="Finaliza la pausa de un monopatin")
    @PatchMapping("/{id}/reanudar")
    public Monopatin reanudar(
    		@Parameter(description = "ID del monopatin a reanudar") 
    		@PathVariable Long id) {
        return service.reanudarMonopatin(id);
    }
    
    @Operation(summary="Obtiene los monopatines mas cercanos desde una ubicacion dada (int latitud, int longitud)")
    @GetMapping("/cercanos")
    public List<Monopatin> cercanos(
    		@Parameter(description = "latitud (double)") 
    		@RequestParam double lat,
    		@Parameter(description = "longitud (double)") 
            @RequestParam double lon
    ) {
        return service.monopatinesCercanos(lat, lon);
    }
    
    @Operation(summary="Cuenta todos los monopatines en mantenimiento")
    @GetMapping("/mantenimiento")
    public int mantenimiento() {
        return service.countMantenimiento();
    }

    @Operation(summary="Cuenta todos los monopatines disponibles")
    @GetMapping("/disponibles")
    public int disponibles() {
        return service.countDisponibles();
    }
    
    @Operation(summary="Cuenta los monopatines en mantenimiento vs los disponibles")
    @GetMapping("/estadistica/estadoMonopatines")
    public Map<String, Long> reportedisponibles() {
        return service.countEstado();
    }
}
