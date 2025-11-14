package servicio;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import modelo.Monopatin;
import repositorio.MonopatinRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class MonopatinService {

    private final MonopatinRepository repository;

    public MonopatinService(MonopatinRepository repository) {
        this.repository = repository;
    }

    public List<Monopatin> findAll() {
        return repository.findAll();
    }

    public Monopatin findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Monopatin no encontrado"));
    }

    public Monopatin create(Monopatin m) {
    	m.setId(null);
        return repository.save(m);
    }

    public Monopatin update(Long id, Monopatin m) {
    	 Monopatin existing = findById(id);
    	
    	    existing.setParada(m.getParada());
    	    existing.setActivo(m.isActivo());
    	    existing.setKilometros(m.getKilometros());
    	    existing.setMantenimiento(m.isMantenimiento());
    	    existing.setLatitud(m.getLatitud());
    	    existing.setLongitud(m.getLongitud());

    	    return repository.save(existing);
    }
    
    public Monopatin partialUpdate(Long id, Map<String, Object> fields) {
        Monopatin m = findById(id);

        if (fields.containsKey("latitud")) {
            Object v = fields.get("latitud");
            if (v == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "latitud no puede ser nula");

            m.setLatitud(Double.parseDouble(v.toString()));
        }

        if (fields.containsKey("longitud")) {
            Object v = fields.get("longitud");
            if (v == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "longitud no puede ser nula");

            m.setLongitud(Double.parseDouble(v.toString()));
        }

        return repository.save(m);
    }

    public void delete(Long id) {
        Monopatin existing = findById(id);
        repository.delete(existing);
    }
    
    public Monopatin comenzarMantenimiento(Long id) {
        Monopatin m = findById(id);

        m.setMantenimiento(true);
        m.setActivo(false);

        return repository.save(m);
    }
    
    public Monopatin terminarMantenimiento(Long id) {
        Monopatin m = findById(id);

        m.setMantenimiento(false);
        m.setActivo(true);

        return repository.save(m);
    }
    
    public List<Monopatin> monopatinesCercanos(double lat, double lon) {
        List<Monopatin> todos = repository.findAll();

        if (todos.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay monopatines");

        return todos.stream()
                .sorted(Comparator.comparingDouble(
                        m -> distancia(lat, lon, m.getLatitud(), m.getLongitud())
                ))
                .collect(Collectors.toList());
    }

    private double distancia(double lat1, double lon1, double lat2, double lon2) {
        double dx = lat1 - lat2;
        double dy = lon1 - lon2;
        return Math.sqrt(dx*dx + dy*dy);
    }
}