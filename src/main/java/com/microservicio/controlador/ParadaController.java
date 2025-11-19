package com.microservicio.controlador;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.modelo.Monopatin;
import com.microservicio.modelo.Parada;
import com.microservicio.servicio.ParadaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/paradas")
public class ParadaController {

    private final ParadaService service;

    public ParadaController(ParadaService service) {
        this.service = service;
    }
    @Operation(summary="Obtiene todas las paradas")
    @GetMapping
    public List<Parada> getAll() {
        return service.findAll();
    }
    
    @Operation(summary="Obtiene una parada especifica")
    @GetMapping("/{id}")
    public Parada getById(@Parameter(description = "ID de la Parada a buscar")@PathVariable Long id) {
        return service.findById(id);
    }
    
    @Operation(summary="Crea una nueva parada")
    @PostMapping
    public ResponseEntity<Parada> create(@RequestBody Parada p) {
        Parada created = service.create(p);
        return ResponseEntity.created(URI.create("/paradas/" + created.getId())).body(created);
    }
    
    @Operation(summary="Modifica los datos de una parada")
    @PutMapping("/{id}")
    public Parada update(@Parameter(description = "ID de la Parada a modificar")@PathVariable Long id, 
    					@RequestBody Parada p) {
        return service.update(id, p);
    }
    
    @Operation(summary="Obtiene todos los monopatines de una parada")
    @GetMapping("/{id}/monopatines")
    public List<Monopatin> getMonopatinesEnParada(@Parameter(description = "ID de la Parada")@PathVariable Long id) {
        return service.getMonopatines(id);
    }

    @Operation(summary="Elimina una parada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID de la Parada a eliminar")@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary="Corrobora si en una parada especifica se encuentra un monopatin en especifico")
    @GetMapping("/{id}/monopatin/{idMonopatin}")
    public Boolean hayMonopatinEnParada(@Parameter(description = "ID de la Parada")@PathVariable Long id,
    									@Parameter(description = "ID del monopatin")@PathVariable Long idMonopatin) {
        return service.getMonopatinByParada(id,idMonopatin);
    }
}
