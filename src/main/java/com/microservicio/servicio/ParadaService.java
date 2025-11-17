package com.microservicio.servicio;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.microservicio.modelo.Monopatin;
import com.microservicio.modelo.Parada;
import com.microservicio.repositorio.ParadaRepository;

@Service
public class ParadaService {

    private static final HttpStatusCode NOT_FOUND = null;
	private final ParadaRepository repository;

    public ParadaService(ParadaRepository repository) {
        this.repository = repository;
    }

    public List<Parada> findAll() {
        return repository.findAll();
    }

    public Parada findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Parada no encontrad"));
    }

    public Parada create(Parada p) {
        if (p.getLatitud() == 0 || p.getLongitud() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Latitud y longitud son obligatorias");
        }
        p.setId(null);
        return repository.save(p);
    }

    public Parada update(Long id, Parada p) {
        Parada existing = findById(id);
        if (p.getLatitud() == 0 || p.getLongitud() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Latitud y longitud son obligatorias");
        }

        existing.setLatitud(p.getLatitud());
        existing.setLongitud(p.getLongitud());
        existing.setMonopatines(p.getMonopatines());

        return repository.save(existing);
    }

    public void delete(Long id) {
        Parada existing = findById(id);
        repository.delete(existing);
    }

	public Boolean getMonopatinByParada(Long id, Long idMonopatin) {
		Parada elegida = repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Parada no encontrada"));
		if(elegida.tieneMonopatin(idMonopatin)) {
			return true;
		}
		return false;
	}

	public List<Monopatin> getMonopatines(Long id) {
		Parada elegida = repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Parada no encontrada"));
		return elegida.getMonopatines();
	}
    
   
}
