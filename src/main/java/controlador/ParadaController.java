package controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import modelo.Monopatin;
import modelo.Parada;
import servicio.ParadaService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/paradas")
public class ParadaController {

    private final ParadaService service;

    public ParadaController(ParadaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Parada> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Parada getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Parada> create(@RequestBody Parada p) {
        Parada created = service.create(p);
        return ResponseEntity.created(URI.create("/paradas/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public Parada update(@PathVariable Long id, @RequestBody Parada p) {
        return service.update(id, p);
    }
    
    @GetMapping("/{id}/monopatines")
    public List<Monopatin> getMonopatinesEnParada(@PathVariable Long id) {
        return service.getMonopatines(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}/monopatin/{idMonopatin}")
    public Boolean hayMonopatinEnParada(@PathVariable Long id,@PathVariable Long idMonopatin) {
        return service.getMonopatinByParada(id,idMonopatin);
    }
}
