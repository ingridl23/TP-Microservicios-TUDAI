package com.microservicio.servicio;

import java.util.List;

import org.springframework.http.HttpStatus;
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
        nuevo.setNombre(dto.getNombre());
        return convertToDTO(repository.save(nuevo));
    }

    public MapaDTO update(Long id, MapaDTO dto) {
        Mapa existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapa no encontrado"));

        existing.setParadasId(dto.getParadasId());
        existing.setNombre(dto.getNombre());
        return convertToDTO(repository.save(existing));
    }

    public void delete(Long id) {
        Mapa existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapa no encontrado"));
        repository.delete(existing);
    }
    
    public MapaDTO agregarParada(Long idMapa, Long idParada) {
        Mapa mapa = repository.findById(idMapa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapa no encontrado"));

        if (!mapa.getParadasId().contains(idParada)) {
            mapa.getParadasId().add(idParada);
        }

        return convertToDTO(repository.save(mapa));
    }
    
    public MapaDTO quitarParada(Long idMapa, Long idParada) {
    	Mapa mapa = repository.findById(idMapa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapa no encontrado"));

        boolean existe = mapa.getParadasId().remove(idParada);

        if (!existe) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "La parada no existe en este mapa"
            );
        }

        return convertToDTO(repository.save(mapa));
    }
    
    public List<Long> obtenerParadas(Long idMapa) {
        Mapa mapa = repository.findById(idMapa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapa no encontrado"));

        return mapa.getParadasId();
    }

    private MapaDTO convertToDTO(Mapa mapa) {
        return new MapaDTO(mapa.getId(), mapa.getParadasId(), mapa.getNombre());
    }


}
