package servicio;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import modelo.Mapa;
import repositorio.MapaRepository;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class MapaService {

    private final MapaRepository repository;

    public MapaService(MapaRepository repository) {
        this.repository = repository;
    }

    public List<Mapa> findAll() {
        return repository.findAll();
    }

    public Mapa findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Mapa no encontrado"));
    }

    public Mapa create(Mapa m) {
        m.setId(null);
        return repository.save(m);
    }

    public Mapa update(Long id, Mapa m) {
        Mapa existing = findById(id);

        existing.setParadas(m.getParadas());
        return repository.save(existing);
    }

    public void delete(Long id) {
        Mapa existing = findById(id);
        repository.delete(existing);
    }
}
