package controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import modelo.Monopatin;
import servicio.MonopatinService;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/monopatines")
public class MonopatinController {

    private final MonopatinService service;

    public MonopatinController(MonopatinService service) {
        this.service = service;
    }

    @GetMapping
    public List<Monopatin> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Monopatin getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Monopatin> create(@RequestBody Monopatin m) {
        Monopatin created = service.create(m);
        return ResponseEntity.created(URI.create("/monopatines/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public Monopatin update(@PathVariable Long id, @RequestBody Monopatin m) {
        return service.update(id, m);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/mantenimiento/inicio")
    public Monopatin comenzar(@PathVariable Long id) {
        return service.comenzarMantenimiento(id);
    }

    @PutMapping("/{id}/mantenimiento/fin")
    public Monopatin terminar(@PathVariable Long id) {
        return service.terminarMantenimiento(id);
    }
    
    @PatchMapping("/{id}/localizacion")
    public Monopatin partial(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        return service.partialUpdate(id, fields);
    }
    
    @GetMapping("/cercanos")
    public List<Monopatin> cercanos(
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        return service.monopatinesCercanos(lat, lon);
    }
    
    @GetMapping("/mantenimiento")
    public int mantenimiento() {
        return service.countMantenimiento();
    }

    @GetMapping("/disponibles")
    public int disponibles() {
        return service.countDisponibles();
    }
}
