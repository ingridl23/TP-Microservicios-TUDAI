package com.microservicio.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import com.microservicio.dto.MonopatinDTO;
import com.microservicio.dto.ParadaDTO;

import com.microservicio.modelo.Parada;
import com.microservicio.repositorio.ParadaRepository;

@Service
public class ParadaService {

    private final ParadaRepository repository;

    public ParadaService(ParadaRepository repository) {
        this.repository = repository;
    }

    public List<ParadaDTO> findAll() {
    	List<Parada> paradas = repository.findAll();
        List<ParadaDTO> dtos = new ArrayList<>();

        for (Parada parada : paradas) {
            ParadaDTO dto = convertToDTO(parada);
            dtos.add(dto);
        }

        return dtos;
    }

    public ParadaDTO findById(Long id) {
        Parada parada = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parada no encontrada"));

        return convertToDTO(parada);
    }
//registra una nueva parada en la BD y no necesita de los monopatines
    public ParadaDTO create(ParadaDTO dto) {
        Parada parada = convertToEntity(dto);
        Parada saved = repository.save(parada);
        return convertToDTO(saved);
        
    }

    public ParadaDTO update(Long id, ParadaDTO dto) {
        Parada existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la parada"));

        existing.setLatitud(dto.getLatitud());
        existing.setLongitud(dto.getLongitud());

        return convertToDTO(repository.save(existing));
    }

    public void delete(Long id) {
        Parada existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la parada"));
        repository.delete(existing);
    }

    public List<MonopatinDTO> getMonopatinesPorRest(Long idParada) {
        //llamo a rest parada para pedir la lista en vez de hacerlo acá
    		RestParada r = new RestParada();
    		MonopatinDTO[] body = r.PedidoRest(idParada);
        List<MonopatinDTO> lista = new ArrayList<>();
        if (body != null) {
            for (MonopatinDTO m : body) {
                lista.add(m);
            }
        }

        return lista;
    }


    public boolean getMonopatinByParada(Long idParada, Long idMonopatin) {
    	List<MonopatinDTO> monopatines = getMonopatinesPorRest(idParada);

        for (MonopatinDTO m : monopatines) {
            if (m.getId().equals(idMonopatin)) {
                return true;
            }
        }

        return false;
    }

    private ParadaDTO convertToDTO(Parada parada) {
        return new ParadaDTO(parada.getId(), parada.getLatitud(), parada.getLongitud(), new ArrayList<>());
    }

    private Parada convertToEntity(ParadaDTO dto) {
        Parada parada = new Parada();
        parada.setLatitud(dto.getLatitud());
        parada.setLongitud(dto.getLongitud());
        return parada;
    }

   
      
}

	 

