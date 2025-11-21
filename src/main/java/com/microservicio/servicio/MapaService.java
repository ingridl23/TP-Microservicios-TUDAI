package com.microservicio.servicio;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.microservicio.dto.MapaDTO;
import com.microservicio.modelo.Mapa;
import com.microservicio.repositorio.MapaRepository;

@Service
public class MapaService {

	private final MapaRepository repository;

    public MapaService(MapaRepository repository) {
        this.repository = repository;
    }

    public List<MapaDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public MapaDTO findById(Long id) {
        Mapa mapa = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapa no encontrado"));
        return convertToDTO(mapa);
    }

    public MapaDTO create(MapaDTO dto) {
        Mapa nuevo = new Mapa();
        nuevo.setParadasId(dto.getParadasId());
        return convertToDTO(repository.save(nuevo));
    }

    public MapaDTO update(Long id, MapaDTO dto) {
        Mapa existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapa no encontrado"));

        existing.setParadasId(dto.getParadasId());
        return convertToDTO(repository.save(existing));
    }

    public void delete(Long id) {
        Mapa existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapa no encontrado"));
        repository.delete(existing);
    }

    private MapaDTO convertToDTO(Mapa mapa) {
        return new MapaDTO(mapa.getId(), mapa.getParadasId());
    }
}
